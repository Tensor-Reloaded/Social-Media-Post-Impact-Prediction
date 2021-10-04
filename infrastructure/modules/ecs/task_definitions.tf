resource "aws_ecs_task_definition" "services" {
    for_each = var.service_names
    family = "${each.key}-family"
    network_mode = each.key == "ui-core-service" ? "awsvpc" : "bridge"
    container_definitions = jsonencode([
        {
          name      = each.key
          image     = var.repositories[each.key]
          cpu       = 256
          memory    = 256
          essential = true
          portMappings = [
            {
              containerPort = 80
            }
          ]
        }
    ])
}
