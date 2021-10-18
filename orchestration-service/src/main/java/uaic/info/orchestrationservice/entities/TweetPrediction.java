package uaic.info.orchestrationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TweetPrediction {
    private Long id;
    private Long userId;
    private Integer predictedNumberOfLikes;
}
