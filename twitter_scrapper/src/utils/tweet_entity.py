from dataclasses import dataclass
import numpy as np


@dataclass
class Tweet:
    tweet_id: int
    tweet_text: str
    tweet_image = np.empty((1, 1))
    user_id: int
    user_followers: int
    tweet_posted_date: str


class TweetFactory:
    pass
