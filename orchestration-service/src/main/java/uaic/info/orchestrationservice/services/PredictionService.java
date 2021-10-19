package uaic.info.orchestrationservice.services;

import lombok.AllArgsConstructor;
import uaic.info.orchestrationservice.clients.AccountManagementClient;
import uaic.info.orchestrationservice.clients.PredictionClient;
import uaic.info.orchestrationservice.entities.TweetPrediction;

/**
 * Facade for handling new predictions
 */
@AllArgsConstructor
public class PredictionService {
    private final PredictionClient predictionClient;
    private final AccountManagementClient accountManagementClient;

    public TweetPrediction predict(String tweetText, String imageData) {
        return null;
    }
}
