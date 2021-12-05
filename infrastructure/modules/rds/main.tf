resource "aws_db_subnet_group" "db_subnet" {
  name       = "smpip_rds_subnet_group"
  subnet_ids = var.db_subnet_ids

  tags = {
    Name = "RDS_subnet_grroups"
  }
}

resource "aws_security_group" "db_sg" {
    name = "smpip_db_sg"
    vpc_id = var.vpc_id

  ingress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    security_groups  = var.allowed_sg_ids 
  }
    
  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }
}

resource "random_password" "ams_password" {
  length           = 16
  special          = false
}

resource "random_string" "ams_user" {
  length           = 8
  special          = false
}

resource "aws_rds_cluster" "ams_postgresql" {
  cluster_identifier      = "smpip-ams-aurora-cluster"
  engine                  = "aurora-postgresql"
  engine_mode             = "serverless"
  availability_zones      = ["${var.region}c", "${var.region}b"]
  database_name           = "smpip"
  master_username         = random_string.ams_user.result
  master_password         = random_password.ams_password.result
  backup_retention_period = 1
  apply_immediately = true
  skip_final_snapshot = true
  db_subnet_group_name    = aws_db_subnet_group.db_subnet.name
  vpc_security_group_ids = [aws_security_group.db_sg.id]
  scaling_configuration {
  max_capacity             = 2
  min_capacity             = 2
  }

  lifecycle {
	ignore_changes = [availability_zones]
  }
}

resource "aws_ssm_parameter" "ams_user" {
  name        = "/config/account-management/spring.datasource.username"
  type        = "SecureString"
  value       = random_string.ams_user.result
}

resource "aws_ssm_parameter" "ams_password" {
  name        = "/config/account-management/spring.datasource.password"
  type        = "SecureString"
  value       = random_password.ams_password.result
}

resource "aws_ssm_parameter" "ams_jdbc_url" {
  name        = "/config/account-management/spring.datasource.url"
  type        = "SecureString"
  value       = "jdbc:postgresql://${aws_rds_cluster.ams_postgresql.endpoint}:5432/${aws_rds_cluster.ams_postgresql.database_name}"
}

resource "random_password" "pms_password" {
  length           = 16
  special          = false
}

resource "random_string" "pms_user" {
  length           = 8
  special          = false
}

resource "aws_rds_cluster" "pms_postgresql" {
  cluster_identifier      = "smpip-pms-aurora-cluster"
  engine                  = "aurora-postgresql"
  engine_mode             = "serverless"
  availability_zones      = ["${var.region}c", "${var.region}b"]
  database_name           = "smpip"
  master_username         = random_string.pms_user.result
  master_password         = random_password.pms_password.result
  backup_retention_period = 1
  apply_immediately = true
  skip_final_snapshot = true
  db_subnet_group_name    = aws_db_subnet_group.db_subnet.name
  vpc_security_group_ids = [aws_security_group.db_sg.id]
  scaling_configuration {
  max_capacity             = 2
  min_capacity             = 2
  }

  lifecycle {
	ignore_changes = [availability_zones]
  }
}

resource "aws_ssm_parameter" "pms_user" {
  name        = "/config/prediction-management/spring.datasource.username"
  type        = "SecureString"
  value       = random_string.pms_user.result
}

resource "aws_ssm_parameter" "pms_password" {
  name        = "/config/prediction-management/spring.datasource.password"
  type        = "SecureString"
  value       = random_password.pms_password.result
}

resource "aws_ssm_parameter" "pms_jdbc_url" {
  name        = "/config/prediction-management/spring.datasource.url"
  type        = "SecureString"
  value       = "jdbc:postgresql://${aws_rds_cluster.pms_postgresql.endpoint}:5432/${aws_rds_cluster.pms_postgresql.database_name}"
}
