from src.tweet_validator import TweetValidator
from src.twitter_scrapping import TwitterScrapper
from src.twitter_scrapping_strategy import SimpleScrappingStrategy, NoneValueScrappingStrategy
from src.tweet_data_exporting import TweetLocalExporter, TweetConsoleExporter, TweetJsonExporter
from src.twitter_importing import TwitterAPIAdapter
from conf.env_secrets import TwitterSecrets
from abc import ABC, abstractmethod
from src.tweet_post_processing import RawDataTweetFactory, TweetFactoryBuilder


class Orchestrator(ABC):
    @abstractmethod
    def main(self):
        """Orchestrates the interaction logic between objects"""


class SimpleConsoleOrchestrator(Orchestrator):
    """
    One-Threaded Tweet exporter that uses simple logic for importing tweet data,
    and exporting it to the console
    """

    def __init__(self, export_file, target_date, **kwargs):
        self.params = kwargs
        self.target_date = target_date
        self.export_file = export_file

    def main(self):
        exporter = TweetJsonExporter(self.export_file)
        importer = TwitterAPIAdapter(TwitterSecrets)
        validation_method = TweetValidator.test
        factory = TweetFactoryBuilder.get_factory(RawDataTweetFactory)
        strategy = SimpleScrappingStrategy(twitter_adapter=importer,
                                           data_exporter=exporter,
                                           tweet_validation_method=validation_method,
                                           tweet_factory=factory,
                                           target_date=self.target_date,
                                           **self.params)
        scrapper = TwitterScrapper(strategy)
        scrapper.start(threads=1)


class NoneValueConsoleOrchestrator(Orchestrator):
    """
    One-Threaded Tweet exporter that uses simple logic for importing tweet data,
    and exporting it to the console
    """

    def __init__(self, **kwargs):
        self.params = kwargs

    def main(self):
        exporter = TweetConsoleExporter()
        importer = TwitterAPIAdapter(TwitterSecrets)
        strategy = NoneValueScrappingStrategy(twitter_adapter=importer,
                                              data_exporter=exporter,
                                              **self.params)
        scrapper = TwitterScrapper(strategy)
        scrapper.start(threads=1)
