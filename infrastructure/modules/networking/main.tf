resource "aws_vpc" "smpip_vpc" {
  cidr_block       = "10.0.0.0/16"
  instance_tenancy = "default"

  tags = {
    Name = "smpip_vpc"
  }
}

# START REGION SUBNETS

resource "aws_subnet" "public_subnet" {
  vpc_id     = aws_vpc.smpip_vpc.id
  cidr_block = "10.0.1.0/24"

  tags = {
    Name = "smpip_public_subnet"
  }
}

resource "aws_subnet" "services_subnet" {
  vpc_id     = aws_vpc.smpip_vpc.id
  cidr_block = "10.0.2.0/24"

  tags = {
    Name = "smpip_services_subnet"
  }
}

resource "aws_subnet" "db_subnet" {
  vpc_id     = aws_vpc.smpip_vpc.id
  cidr_block = "10.0.3.0/24"

  tags = {
    Name = "smpip_db_subnet"
  }
}

# END REGION SUBNETS

# START REGION PUBLIC SUBNET CONFIGURATION
resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.smpip_vpc.id

  tags = {
    Name = "smpip_igw"
  }
}

resource "aws_route_table" "public_route_table" {
  vpc_id = aws_vpc.smpip_vpc.id

  route {
      cidr_block = "0.0.0.0/0"
      gateway_id = aws_internet_gateway.igw.id
  }
  
  tags = {
    Name = "smpip_rt"
  }
}

resource "aws_route_table_association" "public_route_table_association" {
  subnet_id      = aws_subnet.public_subnet.id
  route_table_id = aws_route_table.public_route_table.id
}

# END REGION PUBLIC SUBNET CONFIGURATION

# START REGION PRIVATE SUBNET CONFIGURATION

resource "aws_eip" "private_subnet_eip" {
  vpc = true

  depends_on = [aws_internet_gateway.igw]
  tags = {
    Name = "smpip_eip"
  }
}

resource "aws_nat_gateway" "example" {
  allocation_id = aws_eip.private_subnet_eip.id
  subnet_id     = aws_subnet.services_subnet.id

  tags = {
    Name = "smpip_nat"
  }

  depends_on = [aws_internet_gateway.igw]
}

# END REGION PRIVATE SUBNET CONFIGURATION
