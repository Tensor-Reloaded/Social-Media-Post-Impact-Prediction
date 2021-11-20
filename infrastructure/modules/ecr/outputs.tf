output "repositories" {
  value = tomap({
    for service in var.service_names : service => aws_ecr_repository.service_repositories[service].repository_url
  })
}

