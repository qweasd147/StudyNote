terraform {
  required_version = ">= 1.6.5"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "= 5.30.0"
    }
  }
}

provider "aws" {
  region                   = "ap-northeast-2"
  profile                  = "joo"
  shared_credentials_files = ["~/.aws/credentials_joo"]
}

module "vpc" {
  source = "./vpc"
}

module "eks-cluster" {
  source = "./cluster"

  eks-cluster-private-subnets-id = [for sub-pri in module.vpc.sub-pri : sub-pri.id]
}
