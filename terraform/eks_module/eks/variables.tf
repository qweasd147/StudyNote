variable "cluster_name" {
  type        = string
  description = "eks name"
}

variable "private_subnets_id" {
  type = list(string)
}

variable "vpc_id" {
  type = string
}
