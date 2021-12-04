/*
 * Determine most recent ECS optimized AMI
 */
data "aws_ami" "ecs_ami" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = var.use_amazon_linux2 ? ["amzn2-ami-ecs-hvm-2.0.*-x86_64-ebs"] : ["amzn-ami-*-amazon-ecs-optimized"]
  }
}

/*
 * Creattcloudwatch policy
 */

resource "aws_iam_role_policy" "cloudwatch_policy" {
  name = "ECS-CloudWatchLogs"
  role  = aws_iam_role.ecsInstanceRole.id
  policy = var.cloudWatchPolicy
}


/*
 * Create ECS IAM Instance Role and Policy
 */
resource "random_id" "code" {
  byte_length = 4
}

resource "aws_iam_role" "ecsInstanceRole" {
  name               = "ecsInstanceRole-${random_id.code.hex}"
  assume_role_policy = var.ecsInstanceRoleAssumeRolePolicy
}

resource "aws_iam_role_policy" "ecsInstanceRolePolicy" {
  name   = "ecsInstanceRolePolicy-${random_id.code.hex}"
  role   = aws_iam_role.ecsInstanceRole.id
  policy = var.ecsInstancerolePolicy
}


resource "aws_iam_role_policy_attachment" "ssm_policy_attachment" {
  role = aws_iam_role.ecsInstanceRole.name  
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"    
}

/*
 * Create ECS IAM Service Role and Policy
 */
resource "aws_iam_role" "ecsServiceRole" {
  name               = "ecsServiceRole-${random_id.code.hex}"
  assume_role_policy = var.ecsServiceRoleAssumeRolePolicy
}

resource "aws_iam_role_policy" "ecsServiceRolePolicy" {
  name   = "ecsServiceRolePolicy-${random_id.code.hex}"
  role   = aws_iam_role.ecsServiceRole.id
  policy = var.ecsServiceRolePolicy
}

resource "aws_iam_role_policy" "ecsServiceParamRolePolicy" {
  name   = "ecsServiceParamRolePolicy-${random_id.code.hex}"
  role   = aws_iam_role.ecsInstanceRole.id
  policy = var.ecsServiceParamRolePolicy
}

resource "aws_iam_instance_profile" "ecsInstanceProfile" {
  name = "ecsInstanceProfile-${random_id.code.hex}"
  role = aws_iam_role.ecsInstanceRole.name
}

resource "aws_iam_user" "github_user" {
    name = "smpip_github_user"
    path = "/"
    tags = {
        "Name" = "smpip_github_user"
    }
}

resource "aws_iam_access_key" "github_user_key" {
    user = aws_iam_user.github_user.name
}

resource "aws_iam_user_policy" "github_user_policy" {
  name = "smpip_github_user_policy"
  user = aws_iam_user.github_user.name

  # Terraform's "jsonencode" function converts a
  # Terraform expression result to valid JSON syntax.
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "ec2:Describe*",
        ]
        Effect   = "Allow"
        Resource = "*"
      },
    ]
  })
}

/*
 * ECS related variables
 */

variable "use_amazon_linux2" {
  default     = false
  description = "Use Amazon Linux 2 instead of Amazon Linux"
}

variable "ecsInstanceRoleAssumeRolePolicy" {
  type = string

  default = <<EOF
{
  "Version": "2008-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

variable "ecsInstancerolePolicy" {
  type = string

  default = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ecs:CreateCluster",
        "ecs:DeregisterContainerInstance",
        "ecs:DiscoverPollEndpoint",
        "ecs:Poll",
        "ecs:RegisterContainerInstance",
        "ecs:StartTelemetrySession",
        "ecs:Submit*",
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "sts:AssumeRole"
      ],
      "Resource": "*"
    }
  ]
}
EOF
}

variable "ecsServiceRoleAssumeRolePolicy" {
  type = string

  default = <<EOF
{
  "Version": "2008-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF
}

variable "ecsServiceRolePolicy" {
  default = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:AuthorizeSecurityGroupIngress",
        "ec2:Describe*",
        "elasticloadbalancing:DeregisterInstancesFromLoadBalancer",
        "elasticloadbalancing:DeregisterTargets",
        "elasticloadbalancing:Describe*",
        "elasticloadbalancing:RegisterInstancesWithLoadBalancer",
        "elasticloadbalancing:RegisterTargets"
      ],
      "Resource": "*"
    }
  ]
}
EOF
}

variable "cloudWatchPolicy" {
  default = <<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "logs:CreateLogGroup",
                "logs:CreateLogStream",
                "logs:PutLogEvents",
                "logs:DescribeLogStreams"
            ],
            "Resource": [
                "arn:aws:logs:*:*:*"
            ]
        }
    ]
}
EOF
}

variable "ecsServiceParamRolePolicy" {
    default =<<EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
               "ssm:PutParameter",
               "ssm:GetParameter",
               "ssm:GetParameters",
               "ssm:DeleteParameter",
               "ssm:GetParameterHistory",
               "ssm:DeleteParameters",
               "ssm:GetParametersByPath"
             ],
            "Resource": ["*"]
        }
    ]
}
EOF
}
