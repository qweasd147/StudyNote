
provider "aws" {
  region                   = "ap-northeast-2"
  profile                  = "joo"
  shared_credentials_files = ["/Users/joohyung.kim/.aws/credentials_joo"]
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

/*
resource "aws_key_pair" "terraform-key-pair" {
  key_name   = "tf-key-pair"
  public_key = file("/Users/joohyung.kim/.ssh/tf_key.pub")
  tags = {
    Name = "tf-for-test"
  }
}
*/


resource "aws_key_pair" "terraform-private-key-pair" {
  key_name   = "tf-private-key"
  public_key = file("/Users/joohyung.kim/.ssh/tf_private_key.pub")
  tags = {
    Name = "tf-for-private-test"
  }
}
