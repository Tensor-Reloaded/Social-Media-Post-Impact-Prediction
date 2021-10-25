from abc import ABC, abstractmethod
import tweepy as tw


class TwitterAPIAdapter:

    def __init__(self, secrets):
        self.auth = tw.OAuthHandler(secrets.API_KEY,
                                    secrets.API_SECRET_KEY)

    def get_api(self):
        return tw.API(self.auth)

    @staticmethod
    def get_cursor(method, *args, **kwargs):
        return tw.Cursor(method, *args, **kwargs)


class PhotoImporter:
    @staticmethod
    def import_photo(url):
        pass
