from src.twitter_scrapping import TwitterScrapper
from src.twitter_scrapping_strategy import SimpleScrappingStrategy, ScrappingParameters
from src.tweet_data_exporting import TweetLocalExporter, TweetConsoleExporter
from src.twitter_importing import TwitterAdapter
from conf.env_secrets import TwitterSecrets


def get_parameters() -> ScrappingParameters:
    return ScrappingParameters(query='travel',
                               language='en')


if __name__ == '__main__':
    exporter = TweetConsoleExporter()
    importer = TwitterAdapter(TwitterSecrets)
    strategy_params = get_parameters()
    strategy = SimpleScrappingStrategy(twitter_adapter=importer,
                                       data_exporter=exporter,
                                       params=strategy_params)
    scrapper = TwitterScrapper(strategy)
    scrapper.start(threads=1)
