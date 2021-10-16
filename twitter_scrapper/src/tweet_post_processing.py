from src.utils.tweet_entity import Tweet
from abc import ABC, abstractmethod
from typing import List


class TweetFactoryBuilder:
    _factories = {}

    def __init__(self, class_type):
        self.class_type = class_type

    @staticmethod
    def get_factory(class_type):
        if class_type not in TweetFactoryBuilder._factories.keys():
            TweetFactoryBuilder._factories[class_type.__class__] = class_type()
        return TweetFactoryBuilder._factories[class_type.__class__]


class TweetFactory(ABC):
    @abstractmethod
    def create(self, data) -> Tweet:
        """Processes the raw data of a tweet and returns a Tweet object"""

    def mass_create(self, data) -> List[Tweet]:
        return [self.create(piece) for piece in data]


class RawDataTweetFactory(TweetFactory):

    def create(self, data) -> Tweet:
        """Maps the raw data to the information available in a Tweet object, then creates it"""


class ProcessDataTweetFactory(TweetFactory):

    def create(self, data) -> Tweet:
        """Preprocesses and sanitizes all the data and then creates the Tweet object from it"""


class NoneTweetFactory(TweetFactory):

    def create(self, data) -> Tweet:
        return Tweet()
