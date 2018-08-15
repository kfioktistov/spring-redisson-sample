variable "DOCKER_IMG_TAG" {}

terraform {
  backend "s3" {
    bucket = "digital.pashabank.eks.terraform.configs"
    key    = "redisson-sample.tfstate"                     // IT IS IMPORTANT THIS IS DIFFERENT FOR EACH MICROSERVICE
    region = "us-east-1"
  }
}

module "microservice" {
  source           = "git::ssh://git@github.com/PB-Digital/infra-module-ms?ref=v0.0.35"
  service_name     = "redisson-sample"
  service_version  = "1"
  service_image    = "ictcontact/redisson-sample:${var.DOCKER_IMG_TAG}"                     // TODO - inject build versions here
  service_replicas = "3"                                                                // you can change this
}

output "service_host_name" {
  value = "${module.microservice.service_host_name}"
}
