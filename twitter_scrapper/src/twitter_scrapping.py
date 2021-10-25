from threading import Thread
from src.twitter_scrapping_strategy import TwitterScrappingStrategy


class TwitterScrapper:
    def __init__(self, strategy: TwitterScrappingStrategy):
        self.strategy = strategy

    def start(self, threads=1):
        self.scrap()

    def scrap(self):
        self.strategy.apply()
