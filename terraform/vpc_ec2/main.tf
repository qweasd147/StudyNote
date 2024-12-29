terraform {
  required_version = ">= 1.9.5"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.70.0"
    }
  }
}

provider "aws" {
  region                   = "ap-northeast-2"
  profile                  = "joo"
  shared_credentials_files = ["~/.aws/credentials_joo"]
}

data "aws_key_pair" "for_bastion_key_pair" {
  key_name = "tf-key-pair"

}

data "aws_key_pair" "private_instance_key_pair" {
  key_name = "tf-private-key"
}

locals {
  vpc_cidr_block = "10.0.0.0/16"
  # vpc_cidr_block = "10.1.0.0/16"
  # vpc_cidr_block = "10.2.0.0/16"
  # vpc_cidr_block = "10.3.0.0/16"
}

module "networks" {
  source = "./networks"

  vpc_info = {
    name : "sample_project_vpc"
    cidr_block : local.vpc_cidr_block
  }
  pub-subnets = {
    pub_sub1_2a = {
      cidr_block = cidrsubnet(local.vpc_cidr_block, 8, 1) // 10.0.1.0/24
      az         = "ap-northeast-2a"
    }
    pub_sub2_2c = {
      cidr_block = cidrsubnet(local.vpc_cidr_block, 8, 2) //10.0.2.0/24
      az         = "ap-northeast-2c"
    }
  }
  pri-subnets = {
    pri_sub1_2a = {
      cidr_block = cidrsubnet(local.vpc_cidr_block, 8, 101) //10.0.101.0/24
      az         = "ap-northeast-2a"
    }
    pri_sub2_2c = {
      cidr_block = cidrsubnet(local.vpc_cidr_block, 8, 102) //10.0.102.0/24
      az         = "ap-northeast-2c"
    }
  }
}

module "ec2-web-server" {

  source         = "./ec2"
  vpc-id         = module.networks.vpc-id
  sg_alb_id      = module.alb.sg_alb_id
  vpc_cidr_block = local.vpc_cidr_block

  ec2-instance = {
    "web-2a" = {
      ami           = "ami-014009fa4a1467d53"
      az            = "ap-northeast-2a"
      instance_type = "t3.micro"
      key_pair      = data.aws_key_pair.private_instance_key_pair.key_name
      sg_ids        = []
      subnet_id     = module.networks.sub-pri["pri_sub1_2a"].id
    }
    "web-2c" = {
      ami           = "ami-014009fa4a1467d53"
      az            = "ap-northeast-2c"
      instance_type = "t3.micro"
      key_pair      = data.aws_key_pair.private_instance_key_pair.key_name
      sg_ids        = []
      #subnet_id     = module.networks.sub-pri.pri_sub2_2c.id
      subnet_id = module.networks.sub-pri["pri_sub2_2c"].id
    }
  }

  bastion-ec2-instance = {
    ami              = "ami-014009fa4a1467d53"
    az               = "ap-northeast-2a"
    instance_type    = "t3.micro"
    key_pair         = data.aws_key_pair.for_bastion_key_pair.key_name
    public_subnet_id = module.networks.sub-pub["pub_sub1_2a"].id
    sg_ids           = []
  }


  depends_on = [
    module.networks
  ]
}

module "alb" {
  source = "./alb"

  vpc-id                = module.networks.vpc-id
  link-pub-subnets-id   = [for sub-pub in module.networks.sub-pub : sub-pub.id]
  link-ec2-instances-id = module.ec2-web-server.server-instances-id
}

# module "alaram" {
#   source = "./alarm"

#   alaram_lambda_name             = "sample-project-development-alarm"
#   target_load_balance_arn_suffix = module.alb.created_loadbalancer.arn_suffix
#   target_target_group_arn_suffix = module.alb.created_target_group.arn_suffix
# }


output "alb_dns_name" {
  value = module.alb.alb_dns_name
}

output "server-private-ip" {
  value = module.ec2-web-server.server-instances-private-ip
}

output "bastion-public-ip" {
  value = module.ec2-web-server.bastion-public-ip
}

# output "sns_alaram_name" {
#   value = module.alaram.sns_name
# }
