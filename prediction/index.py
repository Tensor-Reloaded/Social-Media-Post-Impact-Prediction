from flask import Flask, request
from src.request_processing import PredictRequestProcessor, ModelRequestProcessor
import boto3
from botocore.credentials import InstanceMetadataProvider, InstanceMetadataFetcher
import py_eureka_client.eureka_client as eureka_client
import logging

app = Flask(__name__)
PORT = 80


@app.route('/predict', methods=['GET', 'POST'])
def predict():
    """Registry Design Pattern"""
    request_data = request.json
    request_processor = PredictRequestProcessor(request_data)
    return request_processor.get_response()


@app.route('/model', methods=['PUT'])
def model():
    """Model Manager"""
    request_data = request.json
    request_processor = ModelRequestProcessor(request_data)
    return request_processor.get_response()

def before_all():
    logging.info("Runing before all block")
    logging.info("Fetching credentials")
    creds = fetch_credentials()
    session = boto3.Session(
                aws_access_key_id=creds.access_key,
                aws_secret_access_key=creds.secret_key
            )
    ssm_client = boto3.client('ssm')
    logging.info("Fetching remote parameters")
    eureka_parameter = ssm_client.get_parameter(Name='/config/prediction/eureka.client.serviceUrl.defaultZone', WithDecryption=True)
    eureka_endpoint = eureka_parameter['Parameter']['Value']
    register_eureka(eureka_endpoint)
    
def fetch_credentials():
    provider = InstanceMetadataProvider(iam_role_fetcher=InstanceMetadataFetcher(timeout=1000, num_attempts=2))
    return provider.load().get_frozen_credentials()


def register_eureka(eureka_endpoint):
    logging.info(f"Registering eureka client with endpoint {eureka_endpoint}")
    eureka_client.init(eureka_server=eureka_endpoint,
                                app_name="prediction",
                                instance_port=PORT)
    
if __name__ == '__main__':
    before_all()
    app.run(host="127.0.0.1", port=PORT, debug=True)
