resource "aws_iam_role" "role-cluster" {
  name = "role-eks-cluster-${var.cluster_info.name}"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Principal = {
        Service = "eks.amazonaws.com"
      },
      Action = "sts:AssumeRole"
    }]
  })
}
resource "aws_iam_role_policy_attachment" "cluster_AmazonEKSClusterPolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
  role       = aws_iam_role.role-cluster.name
}
resource "aws_iam_role_policy_attachment" "cluster_AmazonEKSVPCResourceController" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
  role       = aws_iam_role.role-cluster.name
}

resource "aws_eks_cluster" "eks-main-cluster" {
  name     = "eks-cluster-${var.cluster_info.name}"
  role_arn = aws_iam_role.role-cluster.arn
  version  = "1.30"

  vpc_config {
    subnet_ids = var.eks-cluster-private-subnets-id
  }
  depends_on = [
    aws_iam_role_policy_attachment.cluster_AmazonEKSClusterPolicy,
    aws_iam_role_policy_attachment.cluster_AmazonEKSVPCResourceController,
  ]
}

// fargate에서 pod 배치 시 사용하는 역할
resource "aws_iam_role" "role-pod-execution" {
  name = "role-${var.cluster_info.name}-eks-pod-execution-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Effect = "Allow",
      Principal = {
        Service = "eks-fargate-pods.amazonaws.com"
      },
      Action = "sts:AssumeRole"
    }]
  })
}

resource "aws_iam_role_policy_attachment" "role-pod-execution-AmazonEKSFargatePodExecutionRolePolicy" {
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSFargatePodExecutionRolePolicy"
  role       = aws_iam_role.role-pod-execution.name
}

# resource "kubernetes_namespace_v1" "default-namespace" {
#   metadata {
#     name = "joo"
#   }
# }

// default/joo/kube-system 네임스페이스 안에 pod에 대해서만 적용
resource "aws_eks_fargate_profile" "default" {
  cluster_name           = aws_eks_cluster.eks-main-cluster.name
  fargate_profile_name   = "fp-default"
  pod_execution_role_arn = aws_iam_role.role-pod-execution.arn
  subnet_ids             = var.eks-cluster-private-subnets-id // 프라이빗 서브넷만 줄 수 있습니다.
  selector {
    namespace = "default"
  }
  selector {
    namespace = "joo"
  }
  selector {
    namespace = "kube-system"
  }
}

resource "aws_ecr_repository" "eks-ecr" {
  name = "${var.cluster_info.name}-aws-container-nginx"
}
