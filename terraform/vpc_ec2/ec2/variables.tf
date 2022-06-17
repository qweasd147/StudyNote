variable "vpc-id" {
  type        = string
  description = "vpc id for security group"
}

variable "sg_alb_id" {

  default = null
}

variable "ec2-instance" {
  type = map(object({
    #key_pair      = optional(string)
    key_pair      = string
    ami           = string
    az            = string
    subnet_id     = string
    sg_ids        = list(string)
    instance_type = string
  }))

  description = "(optional) describe your variable"

  default = {
    "web-2a" = {
      ami           = "ami-014009fa4a1467d53"
      az            = "ap-northeast-2a"
      instance_type = "t2.micro"
      key_pair      = null
      sg_ids        = []
      subnet_id     = "value"
    }
    "web-2c" = {
      ami           = "ami-014009fa4a1467d53"
      az            = "ap-northeast-2a"
      instance_type = "t2.micro"
      key_pair      = null
      sg_ids        = []
      subnet_id     = "value"
    }
  }
}

variable "bastion-ec2-instance" {
  type = object({
    key_pair         = string
    ami              = string
    az               = string
    public_subnet_id = string
    sg_ids           = list(string)
    instance_type    = string
  })

  description = "for ec2 bastion server"
}
