locals {
    svc_names = toset(["account-management", "prediction-management", "orchestration"])
}

resource "aws_ssm_parameter" "issuer" {
  for_each    = local.svc_names
  name        = "/config/${each.key}/issuer"
  type        = "SecureString"
  value       = "smpip"
}


resource "aws_ssm_parameter" "expiration" {
  for_each    = local.svc_names
  name        = "/config/${each.key}/expInMinutes"
  type        = "SecureString"
  value       = "30"
}

resource "random_string" "private_key" {
  length           = 343
  special          = false
}

resource "aws_ssm_parameter" "secret" {
  for_each    = local.svc_names
  name        = "/config/${each.key}/bearerSecretKey"
  type        = "SecureString"
  value       = random_string.private_key.result
}


