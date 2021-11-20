/*
 * Create ECS cluster
 */
resource "aws_ecs_cluster" "smpip_cluster" {
  name = var.cluster_name
  setting {
    name  = "containerInsights"
    value = "enabled"
  }
}
