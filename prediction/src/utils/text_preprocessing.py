import re

url_removal = re.compile(r'https?://\S*')
rt_user_removal = re.compile(r'(RT )?(@\S+)?')
spaces_removal = re.compile(r'\s+')


def sanitize_text(tweet_text):
    tweet_text = rt_user_removal.sub('', tweet_text)
    tweet_text = url_removal.sub('', tweet_text)
    tweet_text = spaces_removal.sub(' ', tweet_text)
    return tweet_text.strip()


def preprocess_text(text):
    return sanitize_text(text)
