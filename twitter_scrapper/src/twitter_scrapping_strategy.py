from abc import ABC, abstractmethod
from src.tweet_post_processing import TweetFactoryBuilder, NoneTweetFactory
from src.tweet_data_exporting import TweetDataExporter
from src.twitter_importing import TwitterAPIAdapter


class TwitterScrappingStrategy(ABC):
    def __init__(self, twitter_adapter: TwitterAPIAdapter, data_exporter: TweetDataExporter, **kwargs):
        self.params = kwargs
        self.data_exporter = data_exporter
        self.twitter_adapter = twitter_adapter

    @abstractmethod
    def apply(self):
        """Applies the strategy"""


class SimpleScrappingStrategy(TwitterScrappingStrategy):

    def apply(self):
        api = self.twitter_adapter.get_api()
        cursor = self.twitter_adapter.get_cursor(api.search_tweets, **self.params)
        for tweet in cursor.items():
            self.data_exporter.export(tweet)


class NoneValueScrappingStrategy(TwitterScrappingStrategy):

    def apply(self):
        tweet_factory = TweetFactoryBuilder(NoneTweetFactory).get_factory()
        api = self.twitter_adapter.get_api()
        cursor = self.twitter_adapter.get_cursor(api.search_tweets, **self.params)
        for tweet in cursor.items():
            self.data_exporter.export(tweet_factory.create(tweet))
