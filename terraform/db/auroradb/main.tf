resource "aws_security_group" "sg-aurora" {
  vpc_id = var.vpc-id

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [var.bastion-sg-id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  lifecycle {
    create_before_destroy = true
  }
}

resource "aws_rds_cluster_parameter_group" "aurora-cluster-param-group" {
  name        = "aurora-param-group"
  family      = "aurora-mysql8.0"
  description = "Aurora MySQL 8.0 parameter group with custom settings"

  parameter {
    name         = "time_zone"
    value        = "Asia/Seoul"
    apply_method = "pending-reboot"
  }
  parameter {
    name  = "character_set_filesystem"
    value = "binary"
  }
  # parameter {
  #   name  = "collation_server"
  #   value = "utf8mb4_general_ci"
  # }
  parameter {
    name  = "long_query_time"
    value = "2"
  }
  parameter {
    name  = "slow_query_log"
    value = "1"
  }
  parameter {
    name         = "performance_schema"
    value        = "1"
    apply_method = "pending-reboot"
  }
  parameter {
    name  = "innodb_print_all_deadlocks"
    value = "1"
  }
}

resource "aws_rds_cluster" "aurora-cluster" {
  cluster_identifier = "${var.db-name}-aurora-cluster"
  engine             = "aurora-mysql"
  engine_version     = var.db-engine-version
  database_name      = var.db-name
  master_username    = var.master-username
  master_password    = var.master-password
  engine_mode        = "provisioned"
  # backup_retention_period = 1

  preferred_backup_window         = "16:00-18:00"         // KST 01:00-03:00
  preferred_maintenance_window    = "Mon:18:00-Mon:19:00" // KST Tue:03:00-Tue:04:00
  db_cluster_parameter_group_name = aws_rds_cluster_parameter_group.aurora-cluster-param-group.name

  skip_final_snapshot = true
  enabled_cloudwatch_logs_exports = [
    "error",
    "slowquery"
  ]

  vpc_security_group_ids = [aws_security_group.sg-aurora.id]
  db_subnet_group_name   = aws_db_subnet_group.db-subnet-group.name
}


resource "aws_rds_cluster_instance" "aurora_instance1" {
  identifier         = "${var.db-name}-instance1"
  cluster_identifier = aws_rds_cluster.aurora-cluster.id

  instance_class = var.instance-type
  engine         = aws_rds_cluster.aurora-cluster.engine
  engine_version = aws_rds_cluster.aurora-cluster.engine_version

  performance_insights_enabled = false
  # performance_insights_retention_period = 7
  publicly_accessible = false

  db_subnet_group_name = aws_db_subnet_group.db-subnet-group.name
  # db_parameter_group_name = aws_db_parameter_group.aurora-param-group.name
}

# resource "aws_rds_cluster_instance" "aurora_instance2" {
#   identifier         = "${var.db-name}-instance2"
#   cluster_identifier = aws_rds_cluster.aurora-cluster.id

#   instance_class = var.instance-type
#   engine         = aws_rds_cluster.aurora-cluster.engine
#   engine_version = aws_rds_cluster.aurora-cluster.engine_version

#   performance_insights_enabled = false
#   # performance_insights_retention_period = 7
#   publicly_accessible = false

#   db_subnet_group_name = aws_db_subnet_group.db-subnet-group.name
#   # db_parameter_group_name = aws_db_parameter_group.aurora-param-group.name
# }


resource "aws_db_subnet_group" "db-subnet-group" {
  name        = "db-pri-subnet-group"
  subnet_ids  = var.pri-subnets-id
  description = "Private DB Subnet Group"
}
