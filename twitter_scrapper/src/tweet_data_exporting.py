from abc import ABC, abstractmethod
from src.utils.tweet_entity import Tweet


class TweetDataExporter(ABC):

    @abstractmethod
    def export(self, data: Tweet):
        pass


class TweetLocalExporter(TweetDataExporter):
    def export(self, data: Tweet):
        pass


class TweetMongoDBExporter(TweetDataExporter):
    def export(self, data: Tweet):
        pass


class TweetConsoleExporter(TweetDataExporter):
    def export(self, data: Tweet):
        # print(data.text)
        # print(data.user.followers_count)
        # try:
        #     print(data.entities)
        # except Exception as e:
        #     pass
        # print(data.created_at)
        print(data)
