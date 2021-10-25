from src.predictions_strategies import PredictionStrategy
from src.utils.tweet_entity import Tweet


class Prediction:
    def __init__(self, strategy: PredictionStrategy):
        self.strategy = strategy

    def predict(self, data: Tweet):
        return self.strategy.apply(data)
