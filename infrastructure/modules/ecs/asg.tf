/*
 * Generate user_data from template file
 */
data "template_file" "user_data" {
  template = file("${path.module}/default_user_data.sh")

  vars = {
    ecs_cluster_name = var.cluster_name
  }
}

/*
 * Create Launch Configuration
 */
resource "aws_launch_configuration" "lc" {
  instance_type        = var.instance_type
  image_id             = data.aws_ami.ecs_ami.id
  iam_instance_profile = aws_iam_instance_profile.ecsInstanceProfile.id
  security_groups      = [aws_security_group.default_sg.id]
  user_data            = var.user_data != "false" ? var.user_data : data.template_file.user_data.rendered

  lifecycle {
    create_before_destroy = true
  }
}

/*
 * Create Auto-Scaling Group
 */
resource "aws_autoscaling_group" "asg" {
  name                      = var.cluster_name
  vpc_zone_identifier       = [var.subnet_id]
  min_size                  = var.min_size
  max_size                  = var.max_size
  launch_configuration      = aws_launch_configuration.lc.id

  tags = concat(
    [
      {
        key                 = "ecs_cluster"
        value               = var.cluster_name
        propagate_at_launch = true
      },
    ]
  )

  lifecycle {
    create_before_destroy = true
  }
}

