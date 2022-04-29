terraform {
  required_version = ">= 1.1.6"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "= 4.6.0"
    }
  }
}

provider "aws" {
  region                   = "ap-northeast-2"
  shared_credentials_files = ["/Users/joohyung/.aws/credentials_joo"]
}


variable "s3-rule-transitions" {
  type = map(object({
    expiration_days           = number
    noncurrent_expiation_days = number
    transitions = list(object({
      days          = number
      storage_class = string
    }))
  }))

  default = {
    "rule-log" = {
      expiration_days           = 365
      noncurrent_expiation_days = 1
      transitions = [
        {
          days          = 30
          storage_class = "STANDARD_IA"
        },
        {
          days          = 90
          storage_class = "ONEZONE_IA"
        },
        {
          days          = 210
          storage_class = "DEEP_ARCHIVE"
        }
      ]
    }
  }
}

resource "aws_s3_bucket" "tf-s3-bucket" {
  bucket = "joo-bucket-test123-321"
}

resource "aws_s3_bucket_public_access_block" "block-tf-bucket" {
  bucket = aws_s3_bucket.tf-s3-bucket.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

resource "aws_s3_bucket_lifecycle_configuration" "tf-lifecycle-rule" {
  bucket = aws_s3_bucket.tf-s3-bucket.id

  dynamic "rule" {
    for_each = var.s3-rule-transitions

    content {

      id = rule.key
      filter {

        and {
          prefix = "log/"

          tags = {
            key   = "status"
            value = "delete"
          }
        }
      }

      dynamic "transition" {

        for_each = rule.value.transitions

        content {
          days          = transition.value.days
          storage_class = transition.value.storage_class
        }
      }

      expiration {
        days = rule.value.expiration_days
      }

      noncurrent_version_expiration {
        noncurrent_days = rule.value.noncurrent_expiation_days
      }

      status = "Enabled"
    }
  }
}

output "bucket-arn" {
  value = aws_s3_bucket.tf-s3-bucket.arn
}
