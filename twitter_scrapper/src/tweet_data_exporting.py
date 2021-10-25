import json
from abc import ABC, abstractmethod
from src.utils.tweet_entity import Tweet
from typing import List


class TweetDataExporter(ABC):
    def __init__(self, path_to_file):
        self.path = path_to_file

    @abstractmethod
    def export(self, data: List[Tweet]):
        pass


class TweetLocalExporter(TweetDataExporter):
    def export(self, data: List[Tweet]):
        pass


class TweetJsonExporter(TweetDataExporter):
    def export(self, data: List[Tweet]):
        export_data = [tweet.to_dict() for tweet in data]
        with open(self.path, "w+") as output_file:
            json.dump(export_data, output_file)


class TweetMongoDBExporter(TweetDataExporter):
    def export(self, data: List[Tweet]):
        pass


class TweetConsoleExporter(TweetDataExporter):
    def export(self, data: List[Tweet]):
        print(data)
