module "eks" {
  source                         = "terraform-aws-modules/eks/aws"
  cluster_name                   = var.cluster_name
  cluster_version                = "1.30"
  version                        = "19.15.3"
  subnet_ids                     = var.private_subnets_id
  vpc_id                         = var.vpc_id
  cluster_endpoint_public_access = true

  eks_managed_node_group_defaults = {
    ami_type = "AL2_x86_64"
  }

  eks_managed_node_groups = {
    one = {
      name = "node-group-1"

      instance_types = ["t3.small"]

      min_size     = 1
      max_size     = 2
      desired_size = 1
    }

    # two = {
    #   name = "node-group-2"

    #   instance_types = ["t3.small"]

    #   min_size     = 1
    #   max_size     = 2
    #   desired_size = 1
    # }
  }

  tags = {
    terraform = "true"
  }
}


data "aws_eks_cluster_auth" "eks" {
  name = module.eks.cluster_name
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


resource "aws_ecr_repository" "eks-ecr" {
  name = "${var.cluster_name}-aws-container-nginx"
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
