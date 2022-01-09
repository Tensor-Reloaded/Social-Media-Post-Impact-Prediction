from flask import Flask, request, jsonify
from src.request_processing import PredictRequestProcessor, ModelRequestProcessor
from initialize import before_all
import logging

app = Flask(__name__)
PORT = 80

@app.route('/predict', methods=['GET', 'POST'])
def predict():
    """Registry Design Pattern"""
    logging.info("POST /predict")
    request_data = request.json
    request_processor = PredictRequestProcessor(request_data)
    logging.info(request_processor.get_response())
    return { "predictedNumberOfLikes": request_processor.get_response() }
    return jsonify(predictedNumbeerOfLikes=int(request_processor.get_response()))


@app.route('/model', methods=['PUT'])
def model():
    """Model Manager"""
    request_data = request.json
    request_processor = ModelRequestProcessor(request_data)
    return request_processor.get_response()

    
if __name__ == '__main__':
    before_all(PORT)
    app.run(host="0.0.0.0", port=PORT, debug=True)
