from dataclasses import dataclass


@dataclass
class Tweet:
    tweet_id: int = None
    tweet_text: str = None
    tweet_image = None
    user_id: int = None
    user_followers: int = None
    tweet_posted_date: str = None
