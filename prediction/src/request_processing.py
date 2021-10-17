from abc import ABC, abstractmethod
from src.model_manager import ModelManager
from src.predictions_strategies import PredictStrategyRegistry
from src.requests_validator import PredictRequestValidator, ModelRequestValidator


class RequestProcessor(ABC):
    def __init__(self, request_data):
        self.request_data = request_data

    @abstractmethod
    def get_response(self):
        """Returns the response after processing it"""
        pass


class PredictRequestProcessor(RequestProcessor):
    def get_response(self):
        if not PredictRequestValidator.is_valid(self.request_data):
            return "Invalid syntax for request", 400
        strategy = PredictStrategyRegistry.get(self.request_data)
        return strategy.apply(None)


class ModelRequestProcessor(RequestProcessor):
    def get_response(self):
        if not ModelRequestValidator.is_valid(self.request_data):
            return "Invalid syntax for request", 400
        ModelManager.change_model(self.request_data)
