from dataclasses import dataclass
import numpy as np
from dataclasses_json import dataclass_json


@dataclass_json
@dataclass
class Tweet:
    tweet_id: int = None
    tweet_text: str = None
    tweet_image: str = None
    tweet_impact: int = None
    tweet_source: str = None
    user_id: int = None
    user_followers: int = None
    tweet_posted_date: str = None
    user_tweets_count: int = None
    retweeted: bool = None
