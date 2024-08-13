output "vpc-id" {
  value = module.vpc.vpc_id
}

output "vpc-arn" {
  value = module.vpc.vpc_arn
}

output "private-subnets" {
  value = module.vpc.private_subnets
}

output "private-subnet-arns" {
  value = module.vpc.private_subnet_arns
}

output "private-subnets-cidr-blocks" {
  value = module.vpc.private_subnets_cidr_blocks
}
