resource "aws_vpc" "main-vpc" {
  cidr_block       = var.vpc_info.cidr_block
  instance_tenancy = "default"

  enable_dns_hostnames = true

  tags = {
    Name = "vpc-${var.vpc_info.name}"
  }
}

resource "aws_subnet" "sub-pub" {

  for_each = var.pub-subnets

  vpc_id     = aws_vpc.main-vpc.id
  cidr_block = each.value.cidr_block

  availability_zone       = each.value.az
  map_public_ip_on_launch = true

  tags = {
    Name                                                 = "${var.vpc_info.name}-sub-pub"
    Public                                               = "true"
    "kubernetes.io/cluster/${var.vpc_info.cluster_name}" = "shared"
    "kubernetes.io/role/elb"                             = "1"
  }
}

resource "aws_subnet" "sub-pri" {

  for_each = var.pri-subnets

  vpc_id     = aws_vpc.main-vpc.id
  cidr_block = each.value.cidr_block

  availability_zone = each.value.az

  tags = {
    Name = "${var.vpc_info.name}-sub-pri"
  }
}

resource "aws_internet_gateway" "igw-vpc" {

  vpc_id = aws_vpc.main-vpc.id

  tags = {
    Name = "igw-vpc-${var.vpc_info.name}"
  }
}

// private subnet -> route table로 인터넷 트래픽이 오면 igw 로 보내버림
resource "aws_route_table" "rt-pub" {
  vpc_id = aws_vpc.main-vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw-vpc.id
  }

  tags = {
    Name   = "rt-pub-sub"
    Public = "true"
  }
}

resource "aws_route_table_association" "rt-pub-as" {

  for_each       = aws_subnet.sub-pub
  subnet_id      = aws_subnet.sub-pub[each.key].id
  route_table_id = aws_route_table.rt-pub.id
}


resource "aws_route_table" "rt-pri" {

  vpc_id   = aws_vpc.main-vpc.id
  for_each = aws_subnet.sub-pri

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.nat-gw[each.key].id
  }

  tags = {
    Name   = "rt-pri-${each.key}"
    Public = "false"
  }
}

resource "aws_route_table_association" "rt-pri-as" {

  for_each       = aws_subnet.sub-pri
  subnet_id      = aws_subnet.sub-pri[each.key].id
  route_table_id = aws_route_table.rt-pri[each.key].id
}

resource "aws_eip" "nat-eip" {
  for_each = var.pri-subnets
  domain   = "vpc"
  // vpc      = true depcreated


  tags = {
    Name = "eip-${each.key}"
  }
}

locals {
  subnets_pri_pub_mapping = zipmap(keys(var.pri-subnets), keys(var.pub-subnets))
}

resource "aws_nat_gateway" "nat-gw" {

  for_each      = var.pri-subnets
  allocation_id = aws_eip.nat-eip[each.key].id
  subnet_id     = aws_subnet.sub-pub[local.subnets_pri_pub_mapping[each.key]].id

  tags = {
    Name = "pub-nat-gw-${each.key}"
  }
}
