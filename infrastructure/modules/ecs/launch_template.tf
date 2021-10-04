data "aws_ami" "amazon_linux_2" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn-ami-*-amazon-ecs-optimized"]
  }
}

resource "aws_launch_template" "smpip_ec2_launch_template" {
  name_prefix   = "smpip_service"
  iam_instance_profile {
    name = aws_iam_instance_profile.ecs_agent.name
  }
  image_id      = data.aws_ami.amazon_linux_2.id
  instance_type = "t3.small"
  user_data            = base64encode("#!/bin/bash\necho ECS_CLUSTER=${var.cluster_name} >> /etc/ecs/ecs.config")

  network_interfaces {
    subnet_id = var.subnet_id  
  }
}
