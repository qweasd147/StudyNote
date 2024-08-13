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

provider "kubernetes" {
  host                   = module.eks.eks-cluster-endpoint
  cluster_ca_certificate = base64decode(module.eks.eks-cluster-certificate-authority-data)
  token                  = module.eks.aws-eks-cluster-auth.token
}

# Terraform에서 helm을 통해 k8s 내 Add-on를 설치할 수 있도록 인증 정보를 제공한다.
provider "helm" {
  kubernetes {
    host                   = module.eks.eks-cluster-endpoint
    cluster_ca_certificate = base64decode(module.eks.eks-cluster-certificate-authority-data)
    token                  = module.eks.aws-eks-cluster-auth.token
  }
}

locals {
  cluster_name = "test_eks"
}

module "vpc" {
  source       = "./vpc"
  cluster_name = local.cluster_name
}

module "eks" {
  source             = "./eks"
  cluster_name       = local.cluster_name
  private_subnets_id = module.vpc.private-subnets
  vpc_id             = module.vpc.vpc-id
}

module "alb" {
  source            = "./aws-alb-controller"
  cluster_name      = module.eks.eks-cluster-name
  vpc_id            = module.vpc.vpc-id
  oidc_provider_arn = module.eks.oidc-provider-arn
}


output "cluster-endpoint" {
  value = module.eks.eks-cluster-endpoint
}

output "cluster-security-group-id" {
  description = "Security group ids attached to the cluster control plane"
  value       = module.eks.eks-cluster-security-group-id
}

output "cluster-name" {
  description = "Kubernetes Cluster Name"
  value       = module.eks.eks-cluster-name
}

output "ecr-repository-url" {
  value = module.eks.ecr-repository-url
}

output "ecr-repository-name" {
  value = module.eks.ecr-repository-name
}
