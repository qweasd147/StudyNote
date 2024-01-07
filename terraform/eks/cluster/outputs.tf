output "eks-cluster-name" {
  value = aws_eks_cluster.eks-main-cluster.id
}

output "eks-cluster-version" {
  value = aws_eks_cluster.eks-main-cluster.version
}


output "eks-cluster-arn" {
  value = aws_eks_cluster.eks-main-cluster.arn
}

output "eks-endpoint" {
  value = aws_eks_cluster.eks-main-cluster.endpoint
}
