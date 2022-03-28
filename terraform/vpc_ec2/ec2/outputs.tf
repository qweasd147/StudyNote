output "server-instances-id" {
  value = [for ec2 in aws_instance.web-server : ec2.id]
}
