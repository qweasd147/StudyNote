variable "vpc-id" {
  type        = string
  description = "vpc id for security group"
}

variable "link-pub-subnets-id" {
  type        = list(string)
  description = "public subnets id for alb"
}

variable "link-ec2-instances-id" {
  type        = list(string)
  description = "ec2 instances id for alb"
}
