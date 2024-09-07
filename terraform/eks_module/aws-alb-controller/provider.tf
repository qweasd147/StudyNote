provider "kubernetes" {
  # alias                  = "eks-provider"
  host                   = var.eks_cluster_endpoint
  cluster_ca_certificate = base64decode(var.eks_cluster_certificate_authority_data)
  token                  = var.eks_cluster_auth_token
}

# Terraform에서 helm을 통해 k8s 내 Add-on를 설치할 수 있도록 인증 정보를 제공한다.
provider "helm" {
  kubernetes {
    host                   = var.eks_cluster_endpoint
    cluster_ca_certificate = base64decode(var.eks_cluster_certificate_authority_data)
    token                  = var.eks_cluster_auth_token
  }
}
