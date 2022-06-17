output "alb_dns_name" {
  value = aws_lb.tf-alb.dns_name

}

output "sg_alb_id" {
  value = aws_security_group.sg-alb.id
}
