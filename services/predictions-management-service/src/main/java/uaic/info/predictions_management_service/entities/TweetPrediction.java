package uaic.info.predictions_management_service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "predictions")
public class TweetPrediction {
    @Id
    @GeneratedValue
    private Long id;
    private String tweetText;
    private byte[] imageData;
    private Integer predictedNumberOfLikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull private User user;
}
