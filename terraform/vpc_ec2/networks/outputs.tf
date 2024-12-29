output "vpc-id" {
  value = aws_vpc.main-vpc.id
}

output "sub-pub" {
  #value = [for sub in aws_subnet.sub-pub : sub.id]
  value = {
    for k, sub in aws_subnet.sub-pub : k => {
      id : sub.id
    }
  }
}

output "sub-pri" {
  #value = [for sub in aws_subnet.sub-pri : sub.id]
  value = {
    for k, sub in aws_subnet.sub-pri : k => {
      id : sub.id
    }
  }
}
