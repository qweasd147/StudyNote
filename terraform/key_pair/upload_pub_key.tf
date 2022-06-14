
provider "aws" {
  region                   = "ap-northeast-2"
  shared_credentials_files = ["/Users/joohyung/.aws/credentials_joo"]
}

terraform {
  required_version = ">= 0.12"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.6.0"
    }
  }
}

resource "aws_key_pair" "terraform-key-pair" {
  key_name   = "tf-key-pair"
  public_key = file("/Users/joohyung/.ssh/tf_key.pub")
  tags = {
    Name = "tf-for-test"
  }
}
