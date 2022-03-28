terraform {
  required_version = ">= 1.1.6"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "= 4.6.0"
    }
  }
}

provider "aws" {
  region                   = "ap-northeast-2"
  shared_credentials_files = ["/Users/joohyung/.aws/credentials_joo"]
}

module "vpc" {
  source = "./vpc"
}

module "ec2-web-server" {

  source = "./ec2"
  vpc-id = module.vpc.vpc-id

  ec2-instance = {
    "web-2a" = {
      ami           = "ami-014009fa4a1467d53"
      az            = "ap-northeast-2a"
      instance_type = "t2.micro"
      key_pair      = null
      sg_ids        = []
      subnet_id     = module.vpc.sub-pri["pri_sub1_2a"].id
    }
    "web-2c" = {
      ami           = "ami-014009fa4a1467d53"
      az            = "ap-northeast-2c"
      instance_type = "t2.micro"
      key_pair      = null
      sg_ids        = []
      #subnet_id     = module.vpc.sub-pri.pri_sub2_2c.id
      subnet_id = module.vpc.sub-pri["pri_sub2_2c"].id
    }
  }

  bastion-ec2-instance = {
    ami              = "ami-014009fa4a1467d53"
    az               = "ap-northeast-2a"
    instance_type    = "t2.micro"
    key_pair         = "tf-key-pair"
    public_subnet_id = module.vpc.sub-pub["pub_sub1_2a"].id
    sg_ids           = []
  }


  depends_on = [
    module.vpc
  ]
}

module "alb" {
  source = "./alb"

  vpc-id                = module.vpc.vpc-id
  link-pub-subnets-id   = [for sub-pub in module.vpc.sub-pub : sub-pub.id]
  link-ec2-instances-id = module.ec2-web-server.server-instances-id

  depends_on = [
    module.ec2-web-server
  ]
}
