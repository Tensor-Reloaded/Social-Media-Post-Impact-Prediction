package uaic.info.orchestrationservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.orchestrationservice.clients.AccountManagementClient;
import uaic.info.orchestrationservice.clients.PredictionClient;
import uaic.info.orchestrationservice.entities.Tweet;
import uaic.info.orchestrationservice.entities.TweetMetaData;
import uaic.info.orchestrationservice.entities.TweetPrediction;

/**
 * Facade for handling new predictions
 */
@Service
@AllArgsConstructor
public class PredictionService {
    private final PredictionClient predictionClient;
    private final AccountManagementClient accountManagementClient;

    public TweetPrediction predict(String tweetText, String imageData) {
        TweetMetaData meta = accountManagementClient.getUserData();
        Tweet tweet = Tweet.builder()
                .text(tweetText)
                .imageData(imageData)
                .meta(meta)
                .build();
        Integer prediction =  predictionClient.predict(tweet);
        return new TweetPrediction(meta.getId(), prediction);
    }
}
