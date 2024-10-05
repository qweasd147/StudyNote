output "alb_dns_name" {
  value = aws_lb.tf-alb.dns_name

}

output "sg_alb_id" {
  value = aws_security_group.sg-alb.id
}


output "created_loadbalancer" {
  value = aws_lb.tf-alb
}

output "created_target_group" {
  value = aws_lb_target_group.alb-tg
}
