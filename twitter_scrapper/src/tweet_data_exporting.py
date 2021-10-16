from abc import ABC, abstractmethod
from src.utils.tweet_entity import Tweet, TweetFactory


class TweetDataExporter(ABC):

    @abstractmethod
    def export(self, data: Tweet):
        pass


class TweetLocalExporter(TweetDataExporter):
    def export(self, data):
        pass


class TweetMongoDBExporter(TweetDataExporter):
    def export(self, data):
        pass


class TweetConsoleExporter(TweetDataExporter):
    def export(self, data):
        print(data)



