variable "alaram_lambda_name" {
  type        = string
  description = "lambda name"
}

variable "target_load_balance_arn_suffix" {
  type = string
}

variable "target_target_group_arn_suffix" {
  type = string
}
