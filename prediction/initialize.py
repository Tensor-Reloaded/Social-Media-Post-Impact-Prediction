import boto3
from botocore.credentials import InstanceMetadataProvider, InstanceMetadataFetcher
import py_eureka_client.eureka_client as eureka_client
import logging
from logging.config import dictConfig
import os
import re
import requests
import json


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

def before_all(port):
    logging.info("Runing before all block")
    details = detect_connection_details()
    logging.info("Fetching remote parameters")
    eureka_endpoint, bucket = fetch_parameters(details)
    os.environ["bucket"] = bucket
    register_eureka(eureka_endpoint, details["address"], port)


def fetch_parameters(details):
    creds = fetch_credentials()
    session = boto3.Session(
                aws_access_key_id=creds.access_key,
                aws_secret_access_key=creds.secret_key,
                aws_session_token=creds.token,
                region_name = details["region"]
            )
    ssm_client = session.client('ssm')
    eureka_parameter = ssm_client.get_parameter(Name='/config/prediction/eureka.client.serviceUrl.defaultZone', WithDecryption=True)
    eureka_endpoint = eureka_parameter['Parameter']['Value']
    bucket = ssm_client.get_parameter(Name='/config/prediction/bucket', WithDecryption=True)
    return eureka_endpoint, bucket['Parameter']['Value']

    
def fetch_credentials():
    logging.info("Fetching credentials")
    provider = InstanceMetadataProvider(iam_role_fetcher=InstanceMetadataFetcher(timeout=1, num_attempts=3))
    creds = provider.load().get_frozen_credentials()
    if creds is None:
        raise "Unable to fetch credentials"
    return creds


def detect_connection_details():
    metadata = fetch_instance_metadata()
    return {
        "region": extract_region(metadata),
        "address": extract_address(metadata)
    }

def fetch_instance_metadata():
    endpoint = os.environ.get("ECS_CONTAINER_METADATA_URI")
    if endpoint is None:
        raise "Could not detect ECS enviroment"
    r = requests.get(endpoint)
    return r.json()


def extract_region(metadata):
    text = metadata['Labels']['com.amazonaws.ecs.task-arn']
    region_search = re.search('^arn:aws:ecs:(.*?):.*$', text, re.IGNORECASE)
    if not region_search:
        raise "Bad metadata response"
    region = region_search.group(1)
    logging.info(f"The detected region is: {region}")
    return region

def extract_address(metadata):
    networks = metadata["Networks"]
    if len(networks) < 1:
        raise "No networks available"
    first_network = networks[0]
    addresses = first_network["IPv4Addresses"]
    if len(addresses) < 1:
        raise "No addresses available"
    return addresses[0]

def register_eureka(eureka_endpoint, address, port):
    logging.info(f"Registering eureka client with endpoint {eureka_endpoint}")
    eureka_client.init(eureka_server=eureka_endpoint,
                                app_name="prediction",
                                instance_port=port,
                                instance_ip=address)
