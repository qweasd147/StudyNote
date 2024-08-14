output "aurora-cluster-endpoint" {
  value = aws_rds_cluster.aurora-cluster.endpoint
}

output "aurora-cluster-reader-endpoint" {
  value = aws_rds_cluster.aurora-cluster.reader_endpoint
}
