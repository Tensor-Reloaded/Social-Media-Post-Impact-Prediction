locals {
    svc_name = "account-management"
}

resource "aws_ssm_parameter" "issuer" {
  name        = "/config/${local.svc_name}/issuer"
  type        = "SecureString"
  value       = "smpip"
}


resource "aws_ssm_parameter" "expiration" {
  name        = "/config/${local.svc_name}/expInMinutes"
  type        = "SecureString"
  value       = "30"
}

resource "random_string" "private_key" {
  length           = 343
  special          = false
}

resource "aws_ssm_parameter" "secret" {
  name        = "/config/${local.svc_name}/bearerSecretKey"
  type        = "SecureString"
  value       = random_string.private_key.result
}


