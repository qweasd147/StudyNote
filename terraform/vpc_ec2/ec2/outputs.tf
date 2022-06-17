output "server-instances-id" {
  value = [for ec2 in aws_instance.web-server : ec2.id]
}

output "server-instances-private-ip" {
  #value = [for ec2 in aws_instance.web-server : ec2.private_ip]
  value = {
    for k, ec2 in aws_instance.web-server : k => ec2.private_ip
  }
}

output "bastion-public-ip" {
  value = aws_instance.bastion.public_ip
}
