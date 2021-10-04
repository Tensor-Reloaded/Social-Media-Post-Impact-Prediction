terraform {
  backend "s3" {
    encrypt = true
    bucket = "smpip-terraform-backend"
    dynamodb_table = "smpip-terraform-state-lock-dynamo"
    key    = "terraform.tfstate"
    region = "eu-west-1"
  }
}

module "networking" {
    source = "../modules/networking"
}
