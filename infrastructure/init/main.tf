terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}
# Configure the AWS Provider
provider "aws" {
  region = "eu-west-1"
}

# Configure S3 bucket for terraform backend
resource "aws_s3_bucket" "s3_bucket_terraform_backend" {
  bucket = "smpip-terraform-backendv2"
  acl    = "private"

  tags = {
    Name = "smpip_terraform_backend"
  }
}

# Configure DynamoDB table for terraform state locking
resource "aws_dynamodb_table" "dynamodb_terraform_state_lock" {
  name           = "smpip-terraform-state-lock-dynamo"
  hash_key       = "LockID"
  read_capacity  = 20
  write_capacity = 20

  attribute {
    name = "LockID"
    type = "S"
  }

  tags = {
    Name = "smpip_terraform_lock"
  }
}

