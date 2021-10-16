from conf import secrets
import tweepy as tw


def main():
    auth = tw.OAuthHandler(secrets.TwitterSecrets.API_KEY,
                           secrets.TwitterSecrets.API_SECRET_KEY)
    api = tw.API(auth)

    for tweet in tw.Cursor(api.search_tweets, q='cute puppies', lang='en').items(10):
        print(tweet.text)


if __name__ == '__main__':
    main()
