package uaic.info.orchestrationservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uaic.info.orchestrationservice.clients.PredictionClient;
import uaic.info.orchestrationservice.clients.PredictionManagementClient;
import uaic.info.orchestrationservice.dto.PredictionRequestDto;
import uaic.info.orchestrationservice.dto.PredictionResponseDto;
import uaic.info.orchestrationservice.dto.TweetPredictionDto;
import uaic.info.orchestrationservice.multipart.PredictionRequest;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class PredictionService {
    private final PredictionClient predictionClient;
    private final PredictionManagementClient predictionManagementClient;

    public TweetPredictionDto predict(PredictionRequest body, Long userId) throws IOException {
        log.info(String.format("Building prediction request for user %d", userId));
        final PredictionRequestDto predictionRequestDto = new PredictionRequestDto(
                body.getTweetText(),
                encodeBase64(body.getImage().getBytes())
        );
        log.info(String.format("Calling prediction service for user %d", userId));
        final PredictionResponseDto response = predictionClient.predict(predictionRequestDto);
        log.info(String.format("Prediction service returned %d number of likes for user %d",
                response.getPredictedNumberOfLikes(),
                userId
        ));
        final TweetPredictionDto tweetPredictionDto = TweetPredictionDto.builder()
                .userId(userId)
                .tweetText(predictionRequestDto.getTweetText())
                .imageData(predictionRequestDto.getB64ImageData())
                .predictedNumberOfLikes(response.getPredictedNumberOfLikes())
                .build();
        log.info(String.format("Calling Prediction Management Client for user %d", userId));
        predictionManagementClient.createPrediction(tweetPredictionDto);
        log.info(String.format("Successfully informed prediction management client for user id %d", userId));
        return tweetPredictionDto;
    }

    private static String encodeBase64(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }
}
