from abc import ABC, abstractmethod
from dataclasses import dataclass
from src.tweet_data_exporting import TweetDataExporter
from src.twitter_importing import TwitterAdapter


@dataclass
class ScrappingParameters:
    query: str
    language: str


class TwitterScrappingStrategy(ABC):
    def __init__(self, twitter_adapter: TwitterAdapter, data_exporter: TweetDataExporter, params: ScrappingParameters):
        self.params = params
        self.data_exporter = data_exporter
        self.twitter_adapter = twitter_adapter

    @abstractmethod
    def apply(self):
        """Applies the strategy"""


class SimpleScrappingStrategy(TwitterScrappingStrategy):

    def apply(self):
        api = self.twitter_adapter.get_api()
        cursor = self.twitter_adapter.get_cursor(api.search_tweets,
                                                 q=self.params.query,
                                                 lang=self.params.language)
        for tweet in cursor.items():
            self.data_exporter.export(tweet)
