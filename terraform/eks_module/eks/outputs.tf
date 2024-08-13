output "ecr-repository-url" {
  value = aws_ecr_repository.eks-ecr.repository_url
}

output "ecr-repository-name" {
  value = aws_ecr_repository.eks-ecr.name
}

output "eks-cluster-arn" {
  value = module.eks.cluster_arn
}

output "eks-cluster-certificate-authority-data" {
  value = module.eks.cluster_certificate_authority_data
}

output "eks-cluster-endpoint" {
  value = module.eks.cluster_endpoint
}

output "eks-cluster-id" {
  value = module.eks.cluster_id
}

output "eks-cluster-name" {
  value = module.eks.cluster_name
}

output "cluster-oidc-issuer-url" {
  value = module.eks.cluster_oidc_issuer_url
}


output "eks-cluster-status" {
  value       = module.eks.cluster_status
  description = "`CREATING`, `ACTIVE`, `DELETING`, `FAILED`"
}

output "eks-cluster-security-group-id" {
  value = module.eks.cluster_security_group_id
}


output "oidc-provider" {
  value = module.eks.oidc_provider
}

output "oidc-provider-arn" {
  value = module.eks.oidc_provider_arn
}

output "cluster-tls-certificate-sha1-fingerprint" {
  value = module.eks.cluster_tls_certificate_sha1_fingerprint
}

output "aws-eks-cluster-auth" {
  value = data.aws_eks_cluster_auth.eks
}
