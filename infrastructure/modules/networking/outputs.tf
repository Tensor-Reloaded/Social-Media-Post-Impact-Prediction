output "vpc_id" {
    value = aws_vpc.smpip_vpc.id
}

output "public_subnet_ids" {
    value = toset([aws_subnet.public_subnet1.id, aws_subnet.public_subnet2.id])
}

output "services_subnet_id" {
    value = aws_subnet.services_subnet.id 
}

output "services_cidr" {
    value = aws_subnet.services_subnet.cidr_block
}
