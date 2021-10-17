package uaic.info.predictions_management_service.daos;

import uaic.info.predictions_management_service.entities.TweetPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetPredictionsDao extends JpaRepository<TweetPrediction, Long> {
}
