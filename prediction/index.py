from flask import Flask, request
from src.request_processing import PredictRequestProcessor, ModelRequestProcessor
from initialize import before_all

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

    
if __name__ == '__main__':
    before_all()
    app.run(host="127.0.0.1", port=PORT, debug=True)
