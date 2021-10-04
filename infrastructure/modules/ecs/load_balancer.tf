resource "aws_lb_target_group" "ui_core_target_group" {
    name = "smpip-ui-core-target-group"
    port = 80
    protocol = "HTTP"
    target_type = "ip"
    vpc_id = var.vpc_id 
}

resource "aws_lb" "smpip_lb" {
  name               = "smpip-lb"
  internal           = false
  load_balancer_type = "application"
  subnets            = var.public_subnet_ids
}

resource "aws_lb_listener" "ui_core_lb_listener" {
    load_balancer_arn = aws_lb.smpip_lb.arn
    port = "80"

    default_action {
        type = "forward"
        target_group_arn = aws_lb_target_group.ui_core_target_group.arn
    }
}
