variable "cluster_info" {
  type = map(string)

  default = {
    name = "test_eks"
  }
}

variable "eks-cluster-private-subnets-id" {
  type        = list(string)
  description = "private subnets id for eks cluster vpc config"
}
