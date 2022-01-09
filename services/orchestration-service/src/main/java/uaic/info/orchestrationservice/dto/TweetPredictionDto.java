package uaic.info.orchestrationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TweetPredictionDto {
    @NotNull private Long userId;
    @NotNull @Min(0) Integer predictedNumberOfLikes;
    @NotNull String tweetText;
    @NotNull private String imageData;
}
