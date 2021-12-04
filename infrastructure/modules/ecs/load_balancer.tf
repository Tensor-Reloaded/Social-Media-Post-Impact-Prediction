resource "aws_lb_target_group" "ui_core_target_group" {
    name = "smpip-ui-core-tg"
    port = 80
    protocol = "HTTP"
    target_type = "ip"
    vpc_id = var.vpc_id 
}

resource "aws_lb_target_group" "orchestration_target_group" {
    name = "smpip-orcheestration-tg"
    port = 80
    protocol = "HTTP"
    target_type = "ip"
    vpc_id = var.vpc_id 

    health_check {
	path = "/actuator/health"
    }
}

resource "aws_lb" "smpip_lb" {
  name               = "smpip-lb"
  internal           = false
  load_balancer_type = "application"
  subnets            = var.public_subnet_ids
  security_groups    = [aws_security_group.lb_sg.id]
}

resource "aws_lb_listener" "ui_core_lb_listener" {
    load_balancer_arn = aws_lb.smpip_lb.arn
    port = "80"

    default_action {
        type = "forward"
        target_group_arn = aws_lb_target_group.ui_core_target_group.arn
    }
}

resource "aws_lb_listener_rule" "backend_forward" {
    listener_arn = aws_lb_listener.ui_core_lb_listener.arn
    priority = 100
    action {
        type = "forward"
        target_group_arn = aws_lb_target_group.orchestration_target_group.arn
    }

    condition {
	path_pattern {
 	    values = ["/api/v1", "/api/v1/*"]
        }
    }
}
