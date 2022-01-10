from abc import ABC, abstractmethod
from src.model_manager import ModelManager
from src.predictions_strategies import PredictStrategyRegistry
from src.requests_validator import PredictRequestValidator, ModelRequestValidator
import base64
import numpy as np
import io
from PIL import Image
from copy import deepcopy


def _preprocess_image_base64(image64):
    base64_decoded = base64.b64decode(image64)
    image = Image.open(io.BytesIO(base64_decoded))
    image = np.array(image)
    return image


class RequestProcessor(ABC):
    def __init__(self, request_data):
        self.request_data = deepcopy(request_data)

    @abstractmethod
    def get_response(self):
        """Returns the response after processing it"""
        pass


class PredictRequestProcessor(RequestProcessor):
    def get_response(self):
        if not PredictRequestValidator.is_valid(self.request_data):
            return "Invalid syntax for request", 400
        if not ModelManager.is_model_loaded():
            return "Model not ready", 405
        data = deepcopy(self.request_data)
        data["b64ImageData"] = _preprocess_image_base64(data["b64ImageData"])
        strategy = PredictStrategyRegistry.get(data)
        return strategy.apply(data)


class ModelRequestProcessor(RequestProcessor):
    def get_response(self):
        if not ModelRequestValidator.is_valid(self.request_data):
            return "Invalid syntax for request", 400
        ModelManager.change_model(self.request_data)
