import re
import string


class TweetTextSanitization:

    @staticmethod
    def remove_unicode(tweet_text):
        tweet_text = tweet_text.encode("ascii", "ignore")
        return tweet_text.decode()

    @staticmethod
    def remove_spaces(tweet_text):
        tweet_text = tweet_text.replace("\n", " ")
        tweet_text = tweet_text.replace("\t", " ")
        tweet_text = tweet_text.replace("\r\n", " ")
        tweet_text = re.sub(" +", " ", tweet_text)
        return tweet_text

    @staticmethod
    def remove_punctuation(tweet_text):
        return "".join(x for x in tweet_text if x not in string.punctuation)

    @staticmethod
    def remove_urls(tweet_text):
        return re.sub(r'https?://\S+', '', tweet_text)
