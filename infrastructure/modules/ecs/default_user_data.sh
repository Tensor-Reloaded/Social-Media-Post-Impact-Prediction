
#!/bin/bash
echo ECS_CLUSTER="${ecs_cluster_name}" >> /etc/ecs/ecs.config
cd /tmp
sudo yum install -y https://s3.amazonaws.com/ec2-downloads-windows/SSMAgent/latest/linux_amd64/amazon-ssm-agent.rpm
sudo systemctl enable amazon-ssm-agent
sudo systemctl start amazon-ssm-agent
sudo yum install amazon-cloudwatch-agent
