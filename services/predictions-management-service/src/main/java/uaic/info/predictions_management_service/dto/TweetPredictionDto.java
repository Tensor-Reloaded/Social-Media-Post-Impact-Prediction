package uaic.info.predictions_management_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uaic.info.predictions_management_service.entities.TweetPrediction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class TweetPredictionDto {
    @NotNull private Long id;
    @NotNull private Long userId;
    @NotNull @Min(0) Integer predictedNumberOfLikes;
    @NotNull String tweetText;
    @NotNull private String imageData;

    @JsonIgnore
    public static TweetPredictionDto of(TweetPrediction tweetPrediction) {
        final TweetPredictionDto tweetPredictionDto = new TweetPredictionDto();
        tweetPredictionDto.setTweetText(tweetPrediction.getTweetText());
        tweetPredictionDto.setImageData(new String(tweetPrediction.getImageData()));
        tweetPredictionDto.setPredictedNumberOfLikes(tweetPrediction.getPredictedNumberOfLikes());
        tweetPredictionDto.setUserId(tweetPrediction.getUser().getId());
        tweetPredictionDto.setId(tweetPrediction.getId());
        return tweetPredictionDto;
    }
}
