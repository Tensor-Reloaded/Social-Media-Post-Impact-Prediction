import boto3
from botocore.credentials import InstanceMetadataProvider, InstanceMetadataFetcher
import py_eureka_client.eureka_client as eureka_client
import logging
from logging.config import dictConfig
import os
import re
import requests


dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {'wsgi': {
        'class': 'logging.StreamHandler',
        'formatter': 'default'
    }},
    'root': {
        'level': 'DEBUG',
        'handlers': ['wsgi']
    }
})

def before_all():
    logging.info("Runing before all block")
    creds = fetch_credentials()
    region = detect_region()
    session = boto3.Session(
                aws_access_key_id=creds.access_key,
                aws_secret_access_key=creds.secret_key,
                aws_session_token=creds.token,
                region_name = region
            )
    logging.info("Fetching remote parameters")
    ssm_client = session.client('ssm')
    eureka_parameter = ssm_client.get_parameter(Name='/config/prediction/eureka.client.serviceUrl.defaultZone', WithDecryption=True)
    eureka_endpoint = eureka_parameter['Parameter']['Value']
    register_eureka(eureka_endpoint)
    
def fetch_credentials():
    logging.info("Fetching credentials")
    provider = InstanceMetadataProvider(iam_role_fetcher=InstanceMetadataFetcher(timeout=1, num_attempts=3))
    creds = provider.load().get_frozen_credentials()
    if creds is None:
        raise "Unable to fetch credentials"
    return creds

def detect_region():
    endpoint = os.environ.get("ECS_CONTAINER_METADATA_URI")
    if endpoint is None:
        raise "Could not detect ECS enviroment"
    r = requests.get(endpoint)
    response = r.json()
    text = response['Labels']['com.amazonaws.ecs.task-arn']
    region_search = re.search('^arn:aws:ecs:(.*?):.*$', text, re.IGNORECASE)
    if not region_search:
        raise "Bad metadata response"
    region = region_search.group(1)
    logging.info(f"The detected region is: {region}")
    return region



def register_eureka(eureka_endpoint):
    logging.info(f"Registering eureka client with endpoint {eureka_endpoint}")
    eureka_client.init(eureka_server=eureka_endpoint,
                                app_name="prediction",
                                instance_port=PORT)