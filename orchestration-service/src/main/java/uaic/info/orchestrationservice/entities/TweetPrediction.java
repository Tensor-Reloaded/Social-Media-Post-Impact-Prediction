package uaic.info.orchestrationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TweetPrediction {
    private final Long userId;
    private final Integer predictedNumberOfLikes;
}
