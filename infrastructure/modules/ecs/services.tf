resource "aws_ecs_service" "services" {
    for_each = toset([ for k in var.service_names: k if k != "ui-core-service"])
    name = each.key
    cluster  = aws_ecs_cluster.smpip_cluster.id
    task_definition = aws_ecs_task_definition.services[each.key].arn
    desired_count = 1
}

resource "aws_ecs_service" "ui_core_service" {
    name = "ui-core-service"
    cluster  = aws_ecs_cluster.smpip_cluster.id
    task_definition = aws_ecs_task_definition.services["ui-core-service"].arn
    desired_count = 1

    network_configuration {
        subnets = [var.subnet_id]
    }

    load_balancer {
        target_group_arn = aws_lb_target_group.ui_core_target_group.arn
        container_name = "ui-core-service"
        container_port = 80
    }
}
