variable "pri-subnets-id" {
  type        = list(string)
  description = "private subnets id for db subnet groups"
}

variable "vpc-id" {
  type = string
}

variable "db-name" {
  type = string
}

variable "master-username" {
  type = string
}

variable "master-password" {
  type = string
}

variable "db-engine-version" {
  type = string
  #default = "8.0.mysql_aurora.3.05.2"
  default = "8.0.mysql_aurora.3.07.1"
}

variable "instance-type" {
  type = string
  #default = "db.t3.small" not support engine version 3
  default = "db.t3.medium"
}

variable "bastion-sg-id" {
  type        = string
  description = "bastion security group id"
}
