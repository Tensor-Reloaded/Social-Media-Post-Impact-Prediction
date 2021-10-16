from src.twitter_scrapping import TwitterScrapper
from src.twitter_scrapping_strategy import SimpleScrappingStrategy, NoneValueScrappingStrategy
from src.tweet_data_exporting import TweetLocalExporter, TweetConsoleExporter
from src.twitter_importing import TwitterAPIAdapter
from conf.env_secrets import TwitterSecrets
from abc import ABC, abstractmethod


class Orchestrator(ABC):
    @abstractmethod
    def main(self):
        """Orchestrates the interaction logic between objects"""


class SimpleConsoleOrchestrator(Orchestrator):
    """
    One-Threaded Tweet exporter that uses simple logic for importing tweet data,
    and exporting it to the console
    """

    def __init__(self, **kwargs):
        self.params = kwargs

    def main(self):
        exporter = TweetConsoleExporter()
        importer = TwitterAPIAdapter(TwitterSecrets)
        strategy = SimpleScrappingStrategy(twitter_adapter=importer,
                                           data_exporter=exporter,
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
