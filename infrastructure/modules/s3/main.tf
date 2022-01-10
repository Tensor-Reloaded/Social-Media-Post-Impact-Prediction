# Configure S3 bucket for model loading

resource "aws_s3_bucket" "s3_bucket_model" {
  bucket = "smpip-model"
  acl    = "public-read"

  tags = {
    Name = "smpip_s3_model"
  }
}

resource "aws_ssm_parameter" "pms_jdbc_url" {
  name        = "/config/prediction/bucket"
  type        = "SecureString"
  value       = aws_s3_bucket.s3_bucket_model.id
}
