resource "random_password" "password" {
  length           = 16
  special          = true
  override_special = "_%@"
}

resource "random_string" "user" {
  length           = 16
  special          = true
  override_special = "/@£$"
}

resource "aws_rds_cluster" "postgresql" {
  cluster_identifier      = "smpip-aurora-cluster"
  engine                  = "aurora-postgresql"
  engine_mode             = "serverless"
  availability_zones      = ["eu-west-1a", "eu-west-1b"]
  database_name           = "smpip"
  master_username         = random_string.user.result
  master_password         = random_password.password.result
  backup_retention_period = 0
  apply_immediately = true
  skip_final_snapshot = true
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
