package uaic.info.predictions_management_service.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "predictions")
public class TweetPrediction {
    @Id
    private Long id;
    private Long userId;
    private Integer predictedNumberOfLikes;
}
