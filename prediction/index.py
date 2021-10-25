from flask import Flask, request
from src.request_processing import PredictRequestProcessor, ModelRequestProcessor

app = Flask(__name__)


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


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=8081, debug=True)
