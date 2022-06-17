locals {

  allow-web-ingresses = {
    http = {
      from_port = 80
      to_port   = 80
      protocol  = "TCP"
    },
    https = {
      from_port = 443
      to_port   = 443
      protocol  = "TCP"
    }
  }
}

resource "aws_security_group" "sg-alb" {
  name        = "sg_allow_alb"
  description = "Allow alb inbound traffic"
  vpc_id      = var.vpc-id

  dynamic "ingress" {
    for_each = local.allow-web-ingresses

    content {
      description = "alb allow ${ingress.key}"
      from_port   = ingress.value.from_port
      to_port     = ingress.value.to_port
      protocol    = ingress.value.protocol
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "sg-alb"
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_lb" "tf-alb" {
  name               = "tf-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.sg-alb.id]
  subnets            = var.link-pub-subnets-id

  enable_deletion_protection = false

  tags = {
    Name = "tf-alb"
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_lb_target_group" "alb-tg" {
  name     = "tf-alb-tg"
  port     = 80
  protocol = "HTTP"
  vpc_id   = var.vpc-id
  #target_type = "ip"

  health_check {
    enabled             = true
    healthy_threshold   = 3
    interval            = 30
    matcher             = "200"
    path                = "/"
    port                = "traffic-port"
    protocol            = "HTTP"
    timeout             = 15
    unhealthy_threshold = 3
  }
}

resource "aws_lb_listener" "alb-ln" {
  load_balancer_arn = aws_lb.tf-alb.arn
  port              = "80"
  protocol          = "HTTP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.alb-tg.arn
  }
}

resource "aws_lb_target_group_attachment" "lb-tg-group-attach" {

  #for_each = toset(var.link-ec2-instances-id)
  #target_id        = each.value

  count            = length(var.link-ec2-instances-id)
  target_group_arn = aws_lb_target_group.alb-tg.arn

  target_id = var.link-ec2-instances-id[count.index]
  port      = 80
}
