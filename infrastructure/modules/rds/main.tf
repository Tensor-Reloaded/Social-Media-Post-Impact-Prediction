resource "random_password" "password" {
  length           = 16
  special          = false
}

resource "random_string" "user" {
  length           = 8
  special          = false
}

resource "aws_rds_cluster" "postgresql" {
  cluster_identifier      = "smpip-aurora-cluster"
  engine                  = "aurora-postgresql"
  engine_mode             = "serverless"
  availability_zones      = ["${var.region}a", "${var.region}b"]
  database_name           = "smpip"
  master_username         = random_string.user.result
  master_password         = random_password.password.result
  backup_retention_period = 1
  apply_immediately = true
  skip_final_snapshot = true
  scaling_configuration {
  max_capacity             = 2
  min_capacity             = 2
  }

  lifecycle {
	ignore_changes = [availability_zones]
  }
}

resource "aws_ssm_parameter" "user" {
  name        = "/smpip/database/user"
  type        = "SecureString"
  value       = random_string.user.result
}

resource "aws_ssm_parameter" "password" {
  name        = "/smpip/database/password"
  type        = "SecureString"
  value       = random_password.password.result
}
