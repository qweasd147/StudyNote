locals {

  alb-ingresses = {
    http = {
      from_port   = 80
      to_port     = 80
      protocol    = "TCP"
      cidr_blocks = ["0.0.0.0/0"]
    },
    https = {
      from_port   = 443
      to_port     = 443
      protocol    = "TCP"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }
}

locals {
  ec2_common_tags = {
    aws_type = "ec2"
  }
  sg_common_tags = {
    aws_type = "sg"
  }
}

resource "aws_security_group" "server-allow-port" {

  name        = "sg_private_servert"
  description = "Allow private web-sg inbound traffic"
  vpc_id      = var.vpc-id

  dynamic "ingress" {
    for_each = local.alb-ingresses

    content {
      description = "alb allow ${ingress.key}"
      from_port   = ingress.value.from_port
      to_port     = ingress.value.to_port
      protocol    = ingress.value.protocol
      cidr_blocks = ingress.value.cidr_blocks
    }
  }

  ingress {
    description     = "allow ssh from bastion instance"
    from_port       = 22
    to_port         = 22
    protocol        = "TCP"
    security_groups = [aws_security_group.allow_bastion_server_ssh.id]
  }

  ingress {
    description = "web from VPC"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.sg_common_tags, {
    Name = "sg-web-server"
  })

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_security_group" "allow_bastion_server_ssh" {

  name        = "sg_bastion_server"
  description = "Allow bastion ssh"
  vpc_id      = var.vpc-id

  ingress {
    description = "allow bastion ssh from public"
    from_port   = 22
    to_port     = 22
    protocol    = "TCP"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.sg_common_tags, {
    Name = "sg-bastion-server"
  })

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_instance" "bastion" {
  ami                    = var.bastion-ec2-instance.ami
  availability_zone      = var.bastion-ec2-instance.az
  subnet_id              = var.bastion-ec2-instance.public_subnet_id
  key_name               = var.bastion-ec2-instance.key_pair
  vpc_security_group_ids = concat([aws_security_group.allow_bastion_server_ssh.id], var.bastion-ec2-instance.sg_ids)
  instance_type          = var.bastion-ec2-instance.instance_type

  tags = merge(local.ec2_common_tags, {
    Name   = "ec2-bastion"
    public = "true"
  })
}


resource "aws_instance" "web-server" {

  for_each = var.ec2-instance

  ami                    = each.value.ami
  availability_zone      = each.value.az
  subnet_id              = each.value.subnet_id
  user_data              = file("${path.module}/init-script.sh")
  vpc_security_group_ids = concat([aws_security_group.server-allow-port.id], each.value.sg_ids)

  instance_type = each.value.instance_type
  key_name      = each.value.key_pair

  tags = merge(local.ec2_common_tags, {
    Name   = each.key
    Public = "true"
  })
}
