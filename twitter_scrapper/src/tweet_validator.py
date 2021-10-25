class TweetValidator:
    @staticmethod
    def test(data):
        if 'media' in data.entities and \
                len(data.entities['media']) >= 1 and \
                data.retweeted == False:
            return True
        return False
