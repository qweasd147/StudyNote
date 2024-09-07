variable "cluster_name" {
  type = string
}

variable "vpc_id" {
  type = string
}

variable "oidc_provider_arn" {
  type = string
}

variable "eks_cluster_endpoint" {
  type = string
}

variable "eks_cluster_certificate_authority_data" {
  type = string
}

variable "eks_cluster_auth_token" {
  type = string
}
