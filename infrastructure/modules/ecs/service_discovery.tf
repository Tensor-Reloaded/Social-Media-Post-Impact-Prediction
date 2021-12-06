resource "aws_service_discovery_private_dns_namespace" "dns_namespace" {
  name        = "orchestration-service.local"
  vpc         = var.vpc_id
}

resource "aws_service_discovery_service" "svc_disc" {
  name = "eureka"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.dns_namespace.id

    dns_records {
      ttl  = 10
      type = "A"
    }
  }
}

locals {
    eureka_endpoint = "http://${aws_service_discovery_service.svc_disc.name}.${aws_service_discovery_private_dns_namespace.dns_namespace.name}/eureka"
}

resource "aws_ssm_parameter" "pms_jdbc_url" {
  for_each    = toset(["prediction-management", "account-management", "prediction"])
  name        = "/config/${each.key}/eureka.client.serviceUrl.defaultZone"
  type        = "SecureString"
  value       = local.eureka_endpoint
}
