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

locals {
  cluster_name = "test_eks"
}

data "aws_caller_identity" "current" {}

module "vpc" {
  source       = "./vpc"
  cluster_name = local.cluster_name
}

module "eks" {
  source             = "./eks"
  cluster_name       = local.cluster_name
  private_subnets_id = module.vpc.private-subnets
  vpc_id             = module.vpc.vpc-id
  admin_role_account = {
    arn        = data.aws_caller_identity.current.arn
    user_id    = data.aws_caller_identity.current.user_id
    account_id = data.aws_caller_identity.current.account_id
  }
}

module "alb" {
  source                                 = "./aws-alb-controller"
  cluster_name                           = module.eks.eks-cluster-name
  eks_cluster_endpoint                   = module.eks.eks-cluster-endpoint
  eks_cluster_certificate_authority_data = module.eks.eks-cluster-certificate-authority-data
  eks_cluster_auth_token                 = module.eks.aws-eks-cluster-auth.token

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

// eks.6
output "cluster-platform-version" {
  value = module.eks.eks-cluster-platform-version
}
