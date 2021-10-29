from abc import ABC, abstractmethod
from src.tweet_post_processing import TweetFactory
from src.tweet_data_exporting import TweetDataExporter
from src.twitter_importing import TwitterAPIAdapter


class TwitterScrappingStrategy(ABC):
    def __init__(self, target_date: str, twitter_adapter: TwitterAPIAdapter, data_exporter: TweetDataExporter,
                 tweet_validation_method, tweet_factory: TweetFactory, **kwargs):
        self.params = kwargs
        self.data_exporter = data_exporter
        self.twitter_adapter = twitter_adapter
        self.tweet_validation_method = tweet_validation_method
        self.tweet_factory = tweet_factory
        self.target_date = target_date

    @abstractmethod
    def apply(self):
        """Applies the strategy"""


class SimpleScrappingStrategy(TwitterScrappingStrategy):

    def apply(self):
        api = self.twitter_adapter.get_api()
        cursor = self.twitter_adapter.get_cursor(api.search_tweets, include_entities=True, **self.params)
        tweet_list = []
        counter = 0
        try:
            for tweet in cursor.items():
                if tweet.created_at.strftime("%Y.%m.%d") != self.target_date:
                    break
                counter += 1
                if self.tweet_validation_method(tweet):
                    tweet_list.append(self.tweet_factory.create(tweet))
        except Exception as e:
            print(e, e.__traceback__)
        print(f"Tried: {counter}")
        print(f"Fetch rate: {round(len(tweet_list) / (counter+0.0000001), ndigits=2)} %")
        print(f"Fetched: {len(tweet_list)}")
        self.data_exporter.export(tweet_list)


class NoneValueScrappingStrategy(TwitterScrappingStrategy):

    def apply(self):
        api = self.twitter_adapter.get_api()
        cursor = self.twitter_adapter.get_cursor(api.search_tweets, **self.params)
        tweet_list = []
        for tweet in cursor.items():
            if self.tweet_validation_method(tweet):
                tweet_list.append(self.tweet_factory.create(tweet))
        self.data_exporter.export(tweet_list)
