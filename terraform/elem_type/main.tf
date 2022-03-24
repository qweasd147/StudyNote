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

variable "list_string" {
  type    = list(string)
  default = ["list", "string", "value"]
}

variable "set_string" {
  type    = set(string)
  default = ["list", "list", "string", "string", "value"]
}

variable "map_num" {
  type = map(number)
  default = {
    "val1" = 1
    "val2" = 2
    "val3" = 3
    "val4" = 4
  }
}

variable "object_key_val" {
  type = object({
    type   = string
    length = number
    is     = bool
  })
  default = {
    type   = "type1"
    length = 5
    is     = false
  }
}

variable "map_object" {
  type = map(object({
    type    = string
    count   = number
    private = bool
  }))

  default = {
    "private_ec2" = {
      count   = 2
      private = true
      type    = "smal"
    }
    "public_ec2" = {
      count   = 3
      private = false
      type    = "large"
    }
  }
}

variable "tuple_test" {
  type = tuple([string, number, bool])

  default = ["test", 1, false]
}

output "list_string" {
  value = var.list_string
}

output "set_string" {
  value = var.set_string

}

output "map_num" {
  value = var.map_num
}

output "map_num_val1" {
  value = var.map_num.val1
}

output "object_key_val" {
  value = var.object_key_val
}

output "object_key_val_type" {
  value = var.object_key_val.type
}

output "map_object" {
  value = var.map_object
}

output "tuple_test" {
  value = var.tuple_test
}

output "tuple_first" {
  value = var.tuple_test[0]
}
