package uaic.info.predictions_management_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TweetPredictionDetails {
    private Long id;
    private String text;
    private Object image;
    private Long userId;
    private Long userFollowers;
    private Date postedDate;
    private Integer predictedNumberOfLikes;
}
