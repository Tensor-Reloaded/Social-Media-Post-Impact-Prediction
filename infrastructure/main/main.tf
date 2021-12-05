terraform {
  backend "s3" {
    encrypt        = true
    bucket         = "smpip-terraform-backend"
    dynamodb_table = "smpip-terraform-state-lock-dynamo"
    key            = "terraform.tfstate"
    region         = "eu-west-1"
  }
  required_providers {
    mongodbatlas = {
      source  = "mongodb/mongodbatlas"
      version = "1.0.2"
    }
  }
}

locals {
  services = toset([
    "orchestration-service",
    "account-management-service",
    "ui-core-service"
  ])
  region = "eu-west-1"
}

module "networking" {
  source = "../modules/networking"
  region = local.region
}

module "ecr" {
  source        = "../modules/ecr"
  service_names = local.services
}

module "ecs" {
  source            = "../modules/ecs"
  region            = local.region
  vpc_id            = module.networking.vpc_id
  public_subnet_ids = module.networking.public_subnet_ids
  subnet_id         = module.networking.services_subnet_id
  allowed_cidr      = module.networking.services_cidr
  desired_capacity  = 3
  min_size          = 2
  max_size          = 3
  service_names     = local.services
  repositories      = module.ecr.repositories
  instance_type     = "t2.micro"
}

module "rds" {
  source = "../modules/rds"
  region = local.region
  vpc_id = module.networking.vpc_id
  db_subnet_ids = module.networking.db_subnet_ids
  allowed_sg_ids = module.ecs.sgs
}
