from abc import ABC, abstractmethod
from random import randint
from src.model_manager import ModelManager


class PredictionStrategy(ABC):
    @staticmethod
    @abstractmethod
    def apply(data):
        """Applies the strategy"""


class RandomStrategy(PredictionStrategy):
    @staticmethod
    def apply(data):
        return str(randint(0, 1000000))


class ModelPredictionStrategy(PredictionStrategy):
    @staticmethod
    def apply(data):
        image = data["b64ImageData"]
        text = data["tweetText"]
        return ModelManager.get_one_model_prediction(image, text)


class PredictStrategyRegistry:
    @staticmethod
    def get(data):
        return ModelPredictionStrategy
