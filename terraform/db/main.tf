terraform {
  required_version = ">= 1.6.5"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "= 5.62.0"
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

module "vpc" {
  source = "./vpc"
}

module "auroradb" {
  source          = "./auroradb"
  vpc-id          = module.vpc.vpc-id
  bastion-sg-id   = aws_security_group.sg-bastion.id
  pri-subnets-id  = [for sub-pri in module.vpc.sub-pri : sub-pri.id]
  db-name         = "joo"
  master-username = "admin"
  master-password = "admin1234!"
}

### Bastion 서버 설정
resource "aws_instance" "bastion" {
  ami                    = "ami-014009fa4a1467d53"
  instance_type          = "t2.micro"
  subnet_id              = module.vpc.sub-pub["pub_sub1_2a"].id
  vpc_security_group_ids = [aws_security_group.sg-bastion.id]

  key_name = data.aws_key_pair.for_bastion_key_pair.key_name

  associate_public_ip_address = true

  tags = {
    Name = "BastionHost"
  }
}

resource "aws_security_group" "sg-bastion" {
  vpc_id      = module.vpc.vpc-id
  name        = "sg_bastion_server"
  description = "Allow bastion ssh"

  ingress {
    description = "allow bastion ssh from public"
    from_port   = 22
    to_port     = 22
    protocol    = "TCP"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  lifecycle {
    create_before_destroy = true
  }
}


output "bastion-public-ip" {
  value = aws_instance.bastion.public_ip
}

output "aurora-cluster-endpoint" {
  value = module.auroradb.aurora-cluster-endpoint
}

output "aurora-cluster-reader-endpoint" {
  value = module.auroradb.aurora-cluster-endpoint
}
