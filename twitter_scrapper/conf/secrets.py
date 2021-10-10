import os


class TwitterSecrets:
    API_KEY = os.environ.get("TWITTER_API_KEY")
    API_SECRET_KEY = os.environ.get("TWITTER_API_SECRET_KEY")
    BEARER_TOKEN = os.environ.get("TWITTER_API_BEARER_TOKEN")
