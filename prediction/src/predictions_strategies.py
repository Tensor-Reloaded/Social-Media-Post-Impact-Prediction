from abc import ABC, abstractmethod
from random import randint


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
        text = data["tweetText"]
        image64encoded = data["b64ImageData"]


class PredictStrategyRegistry:
    @staticmethod
    def get(data):
        return ModelPredictionStrategy
