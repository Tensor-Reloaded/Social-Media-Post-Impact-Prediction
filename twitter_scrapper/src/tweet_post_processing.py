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

    @staticmethod
    def get_favorite_count(data):
        try:
            return data.retweeted_status.favorite_count
        except:
            return data.favorite_count


class RawDataTweetFactory(TweetFactory):

    def create(self, data) -> Tweet:
        """Maps the raw data to the information available in a Tweet object, then creates it"""
        return Tweet(
            tweet_id=data.id,
            tweet_text=data.text.replace('"', "'"),
            tweet_image=data.entities['media'][0]['media_url'],
            tweet_impact=self.get_favorite_count(data),
            tweet_posted_date=data.created_at.strftime("%Y.%m.%d"),
            tweet_source=data.source,
            user_id=data.author.id,
            user_followers=data.author.followers_count,
            user_tweets_count=data.author.statuses_count,
            retweeted=data.retweeted
        )


class ProcessDataTweetFactory(TweetFactory):

    def create(self, data) -> Tweet:
        """Preprocesses and sanitizes all the data and then creates the Tweet object from it"""


class NoneTweetFactory(TweetFactory):

    def create(self, data) -> Tweet:
        return Tweet()
