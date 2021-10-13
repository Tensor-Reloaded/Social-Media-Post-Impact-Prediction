from flask import Flask, request
from random import randint

app = Flask(__name__)


@app.route('/predict', methods=['POST'])
def predict():
    return str(randint(0, 1000000))


if __name__ == '__main__':
    app.run(host="127.0.0.1", port=8080, debug=True)
