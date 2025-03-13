terraform {
  required_version = ">= 1.9.5"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.70.0"
    }
  }
}

provider "aws" {
  region                   = "ap-northeast-2"
  profile                  = "joo"
  shared_credentials_files = ["~/.aws/credentials"]
}


locals {
  user_names = ["user1_test", "user2_test"]
}

resource "aws_iam_user" "tf-iam-users" {
  for_each = toset(local.user_names)
  name     = each.value
  tags = {
    "Owner" = each.value
  }
}

resource "aws_iam_user_group_membership" "tf_user_group_membership" {
  for_each = toset(local.user_names)
  user     = aws_iam_user.tf-iam-users[each.value].name
  groups   = ["developers"]
}

resource "aws_iam_user_login_profile" "tf_user_login_profile" {
  for_each                = toset(local.user_names)
  user                    = aws_iam_user.tf-iam-users[each.value].name
  password_reset_required = false
}

resource "aws_iam_access_key" "tf_user_access_key" {
  for_each = toset(local.user_names)
  user     = aws_iam_user.tf-iam-users[each.value].name
}

resource "aws_iam_access_key" "tf_user_password" {
  for_each = toset(local.user_names)
  user     = aws_iam_user.tf-iam-users[each.value].name
}

output "access_key_ids" {
  value       = { for user, access_key in aws_iam_access_key.tf_user_access_key : user => access_key.id }
  description = "Access key IDs for the IAM users."
}

output "secret_access_keys" {
  value       = { for user, access_key in aws_iam_access_key.tf_user_access_key : user => access_key.secret }
  description = "Secret access keys for the IAM users."
  sensitive   = true
}
