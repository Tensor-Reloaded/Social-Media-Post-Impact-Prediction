package uaic.info.predictions_management_service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "predictions")
public class TweetPrediction extends BaseEntity {
    private Integer predictedNumberOfLikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
