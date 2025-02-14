
locals {
  vpc_cidr_block = "192.168.0.0/16"
}

module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.0.0"

  name = "eks-vpc"
  cidr = local.vpc_cidr_block

  azs = ["ap-northeast-2a", "ap-northeast-2b", "ap-northeast-2c"]
  public_subnets = [
    cidrsubnet(local.vpc_cidr_block, 8, 1),
    cidrsubnet(local.vpc_cidr_block, 8, 2),
    cidrsubnet(local.vpc_cidr_block, 8, 3)
  ]
  private_subnets = [
    cidrsubnet(local.vpc_cidr_block, 8, 101),
    cidrsubnet(local.vpc_cidr_block, 8, 102),
    cidrsubnet(local.vpc_cidr_block, 8, 103)
  ]

  enable_nat_gateway   = true
  single_nat_gateway   = true
  enable_dns_hostnames = true

  public_subnet_names = ["${var.cluster_name}_pub_subnet_2a", "${var.cluster_name}_pub_subnet_2b", "${var.cluster_name}_pub_subnet_2c"]
  public_subnet_tags = {
    "kubernetes.io/cluster/${var.cluster_name}" = "shared"
    "kubernetes.io/role/elb"                    = 1
  }

  private_subnet_names = ["${var.cluster_name}_pri_subnet_2a", "${var.cluster_name}_pri_subnet_2b", "${var.cluster_name}_pri_subnet_2c"]
  private_subnet_tags = {
    "kubernetes.io/cluster/${var.cluster_name}" = "shared"
    "kubernetes.io/role/internal-elb"           = 1
  }

  tags = {
    Name      = "eks-vpc"
    terraform = "true"
  }
}
