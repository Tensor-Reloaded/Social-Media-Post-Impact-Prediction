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
