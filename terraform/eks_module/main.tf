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

locals {
  cluster_name = "test_eks"
}

module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.0.0"

  name = "eks-vpc"
  cidr = "10.0.0.0/16"

  azs             = ["ap-northeast-2a", "ap-northeast-2b", "ap-northeast-2c"]
  public_subnets  = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  private_subnets = ["10.0.101.0/24", "10.0.102.0/24", "10.0.103.0/24"]

  enable_nat_gateway   = true
  single_nat_gateway   = true
  enable_dns_hostnames = true

  public_subnet_tags = {
    "kubernetes.io/cluster/${local.cluster_name}" = "shared"
    "kubernetes.io/role/elb"                      = 1
  }

  private_subnet_tags = {
    "kubernetes.io/cluster/${local.cluster_name}" = "shared"
    "kubernetes.io/role/internal-elb"             = 1
  }

  tags = {
    Name      = "eks-vpc"
    terraform = "true"
  }
}

module "eks" {
  source                         = "terraform-aws-modules/eks/aws"
  cluster_name                   = local.cluster_name
  cluster_version                = "1.30"
  version                        = "19.15.3"
  subnet_ids                     = module.vpc.private_subnets
  vpc_id                         = module.vpc.vpc_id
  cluster_endpoint_public_access = true

  eks_managed_node_group_defaults = {
    ami_type = "AL2_x86_64"
  }

  eks_managed_node_groups = {
    one = {
      name = "node-group-1"

      instance_types = ["t3.small"]

      min_size     = 1
      max_size     = 3
      desired_size = 2
    }

    two = {
      name = "node-group-2"

      instance_types = ["t3.small"]

      min_size     = 1
      max_size     = 2
      desired_size = 1
    }
  }

  tags = {
    terraform = "true"
  }
}

data "aws_iam_policy" "ebs_csi_policy" {
  arn = "arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy"
}

module "irsa-ebs-csi" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-assumable-role-with-oidc"
  version = "4.7.0"

  create_role                   = true
  role_name                     = "AmazonEKSTFEBSCSIRole-${module.eks.cluster_name}"
  provider_url                  = module.eks.oidc_provider
  role_policy_arns              = [data.aws_iam_policy.ebs_csi_policy.arn]
  oidc_fully_qualified_subjects = ["system:serviceaccount:kube-system:ebs-csi-controller-sa"]
}

resource "aws_eks_addon" "ebs-csi" {
  cluster_name             = module.eks.cluster_name
  addon_name               = "aws-ebs-csi-driver"
  addon_version            = "v1.33.0-eksbuild.1"
  service_account_role_arn = module.irsa-ebs-csi.iam_role_arn
  tags = {
    "eks_addon" = "ebs-csi"
    "terraform" = "true"
  }
}

resource "aws_eks_addon" "vpc-cni" {
  cluster_name  = module.eks.cluster_name
  addon_name    = "vpc-cni"
  addon_version = "v1.18.2-eksbuild.1"
  tags = {
    "eks_addon" = "vpc-cni"
    "terraform" = "true"
  }
}

resource "aws_ecr_repository" "eks-ecr" {
  name = "${local.cluster_name}-aws-container-nginx"
}

# resource "aws_eks_addon" "coredns" {
#   cluster_name  = module.eks.cluster_name
#   addon_name    = "coredns"
#   addon_version = "v1.11.1-eksbuild.9"
#   tags = {
#     "eks_addon" = "coredns"
#     "terraform" = "true"
#   }
# }

# resource "aws_eks_addon" "kube-proxy" {
#   cluster_name  = module.eks.cluster_name
#   addon_name    = "kube-proxy"
#   addon_version = "v1.30.0-eksbuild.3"
#   tags = {
#     "eks_addon" = "kube-proxy"
#     "terraform" = "true"
#   }
# }

output "cluster_endpoint" {
  description = "Endpoint for EKS control plane"
  value       = module.eks.cluster_endpoint
}

output "cluster_security_group_id" {
  description = "Security group ids attached to the cluster control plane"
  value       = module.eks.cluster_security_group_id
}

output "cluster_name" {
  description = "Kubernetes Cluster Name"
  value       = module.eks.cluster_name
}

output "ecr_repository" {
  description = "Kubernetes Cluster Name"
  value       = module.eks.cluster_name
}

output "ecr_repository_url" {
  value = aws_ecr_repository.eks-ecr.repository_url
}

output "ecr_repository_name" {
  value = aws_ecr_repository.eks-ecr.name
}
