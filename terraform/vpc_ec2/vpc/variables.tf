variable "vpc_info" {
  #default = "test_vpc"
  type = map(string)

  default = {
    name       = "test_vpc"
    cidr_block = "10.0.0.0/16"
  }
}

variable "vpc_cidr_block" {
  default = "10.0.0.0/16"
  type    = string
}

variable "pub-subnets" {
  type = map(object({
    cidr_block = string
    az         = string
  }))
  default = {
    pub_sub1_2a = {
      cidr_block = "10.0.1.0/24"
      #cidr_block = cidrsubnet(var.vpc_cidr_block, 8, 1)
      az = "ap-northeast-2a"
    }
    pub_sub2_2c = {
      cidr_block = "10.0.2.0/24"
      #cidr_block = cidrsubnet(var.vpc_cidr_block, 8, 2)
      az = "ap-northeast-2c"
    }
  }
}

# 주의! 현재 private subnet과 public subnet 갯수가 똑같아야함
variable "pri-subnets" {
  type = map(object({
    cidr_block = string
    az         = string
  }))
  default = {
    pri_sub1_2a = {
      cidr_block = "10.0.3.0/24"
      #cidr_block = cidrsubnet(var.vpc_cidr_block, 8, 3)
      az = "ap-northeast-2a"
    }
    pri_sub2_2c = {
      cidr_block = "10.0.4.0/24"
      #cidr_block = cidrsubnet(var.vpc_cidr_block, 8, 4)
      az = "ap-northeast-2c"
    }
  }
}
