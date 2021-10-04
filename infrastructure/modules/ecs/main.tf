resource "aws_iam_service_linked_role" "ecs" {
  aws_service_name = "ecs.amazonaws.com"
}

resource "aws_autoscaling_group" "smpip_asg" {
  name                      = "smpip-asg"
  max_size                  = var.max_size
  min_size                  = var.min_size
  desired_capacity          = var.desired_capacity
  health_check_grace_period = 300
  health_check_type         = "ELB"

  launch_template {
    id      = aws_launch_template.smpip_ec2_launch_template.id
    version = "$Latest"
  }
}

resource "aws_ecs_capacity_provider" "asg_capacity_provider" {
  name = "smpip-capacity-provider"
  auto_scaling_group_provider {
    auto_scaling_group_arn = aws_autoscaling_group.smpip_asg.arn
  }
  depends_on = [aws_iam_service_linked_role.ecs]
}

resource "aws_ecs_cluster" "smpip_cluster" {
  name = var.cluster_name
  capacity_providers = [aws_ecs_capacity_provider.asg_capacity_provider.name]  
}
