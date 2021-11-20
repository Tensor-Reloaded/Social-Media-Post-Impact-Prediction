resource "aws_ecr_repository" "service_repositories" {
  for_each             = var.service_names
  name                 = "${each.key}-bar"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "smpip-${each.key}-ecr"
  }
}

