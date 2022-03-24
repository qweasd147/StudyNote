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
