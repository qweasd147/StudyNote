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

variable "admin_role_account" {
  type = object({
    arn        = string
    user_id    = string
    account_id = string
  })
  description = "aws_caller_identity"
}
