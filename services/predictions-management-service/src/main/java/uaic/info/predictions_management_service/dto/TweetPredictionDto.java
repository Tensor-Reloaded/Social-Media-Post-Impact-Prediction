package uaic.info.predictions_management_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class TweetPredictionDto {
    @NotNull private Long userId;
    @NotNull @Min(0) Integer predictedNumberOfLikes;
    @NotNull String tweetText;
    @NotNull private String imageData;
}
