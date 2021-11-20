package uaic.info.predictions_management_service.services;

import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Service
public class TwitterService {
    public Status getTweetById(Long id) throws TwitterException {
        return TwitterFactory.getSingleton()
                .showStatus(id);
    }
}
