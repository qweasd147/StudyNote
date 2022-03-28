resource "aws_security_group" "server-allow-web" {
  name        = "allow_web-sg"
  description = "Allow web-sg inbound traffic"
  vpc_id      = var.vpc-id

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

  tags = {
    Name = "server-allow-web"
  }

  lifecycle {
    create_before_destroy = true
  }
}

locals {
  ec2_common_tags = {
    aws_type = "ec2"
  }
}

resource "aws_instance" "bastion" {
  ami                    = var.bastion-ec2-instance.ami
  availability_zone      = var.bastion-ec2-instance.az
  subnet_id              = var.bastion-ec2-instance.public_subnet_id
  key_name               = var.bastion-ec2-instance.key_pair
  vpc_security_group_ids = concat([aws_security_group.server-allow-web.id], var.bastion-ec2-instance.sg_ids)
  instance_type          = var.bastion-ec2-instance.instance_type

  tags = merge(local.ec2_common_tags, {
    Name = "ec2-bastion"
  })
}


resource "aws_instance" "web-server" {

  for_each = var.ec2-instance

  ami                    = each.value.ami
  availability_zone      = each.value.az
  subnet_id              = each.value.subnet_id
  user_data              = file("${path.module}/init-script.sh")
  vpc_security_group_ids = concat([aws_security_group.server-allow-web.id], each.value.sg_ids)

  instance_type = each.value.instance_type
  key_name      = each.value.key_pair

  tags = {
    Name = each.key
  }
}
