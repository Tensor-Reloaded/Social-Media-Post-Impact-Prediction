variable "cluster_name" {
    default = "smpip-cluster"
}

variable "region" {}

variable "vpc_id" {}

variable "subnet_id" {}

variable "allowed_cidr" {}

variable "public_subnet_ids" {
    type = set(string)
}

variable "desired_capacity" {
  type = number
}

variable "min_size" {
  type = number
}

variable "max_size" {
  type = number
}

variable "service_names" {
    type = set(string)
}

variable "repositories" {
    type = map(any)
}

variable "user_data" {
  description = "Bash code for inclusion as user_data on instances. By default contains minimum for registering with ECS cluster"
  default     = "false"
}

variable "instance_type" {
  default     = "t2.micro"
}
