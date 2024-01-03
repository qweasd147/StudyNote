variable "vpc_info" {
  type = map(string)

  default = {
    name         = "eks_vpc"
    cidr_block   = "10.0.0.0/16"
    cluster_name = "eks-cluster-test_eks"
  }
}

variable "pub-subnets" {
  type = map(object({
    cidr_block = string
    az         = string
  }))
  default = {
    pub_sub1_2a = {
      cidr_block = "10.0.1.0/24"
      az         = "ap-northeast-2a"
    }
    pub_sub2_2c = {
      cidr_block = "10.0.2.0/24"
      az         = "ap-northeast-2c"
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
      az         = "ap-northeast-2a"
    }
    pri_sub2_2c = {
      cidr_block = "10.0.4.0/24"
      az         = "ap-northeast-2c"
    }
  }
}
