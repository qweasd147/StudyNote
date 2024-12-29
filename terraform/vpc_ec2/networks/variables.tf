variable "vpc_info" {
  type = object({
    name       = string
    cidr_block = string
  })

  default = {
    name       = "test_vpc"
    cidr_block = "10.0.0.0/16"
  }
}

variable "pub-subnets" {
  type = map(object({
    cidr_block = string
    az         = string
  }))
}

# 주의! 현재 private subnet과 public subnet 갯수가 똑같아야함
variable "pri-subnets" {
  type = map(object({
    cidr_block = string
    az         = string
  }))
}
