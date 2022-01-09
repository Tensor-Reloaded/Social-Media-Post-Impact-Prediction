resource "aws_cloudwatch_log_group" "yada" {
  for_each = var.service_names
  name = "/ecs/${each.key}"
}

resource "aws_ecs_task_definition" "services" {
    for_each = var.service_names
    family = "${each.key}-family"
#    network_mode = contains(["ui-core-service", "orchestration-service"], each.key) ? "awsvpc" : "bridge"
    network_mode = "awsvpc"
    container_definitions = jsonencode([
        {
          name      = each.key
          image     = var.repositories[each.key]
          cpu       = 512
          memory    = 850
          essential = true
          portMappings = [
            {
              containerPort = 80
            }
          ]
	  logConfiguration = {
	      logDriver = "awslogs"
	      options = {
 	        awslogs-group = "/ecs/${each.key}"
                awslogs-region = var.region
                awslogs-stream-prefix = "ecs"
 	      }
          }
        }
    ])
}
