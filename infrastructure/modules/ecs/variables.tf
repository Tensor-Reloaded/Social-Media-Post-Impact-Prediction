variable "cluster_name" {
    default = "smpip-cluster"
}

variable "vpc_id" {}

variable "subnet_id" {}

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
