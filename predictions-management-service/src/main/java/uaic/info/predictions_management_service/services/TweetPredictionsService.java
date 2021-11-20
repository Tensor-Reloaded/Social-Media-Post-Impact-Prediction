package uaic.info.predictions_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uaic.info.predictions_management_service.daos.TweetPredictionsDao;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TweetPredictionsService {
    private static final String TWEET_PREDICTION_ENTITY = "TweetPrediction";
    private final TweetPredictionsDao tweetPredictionsDao;

    public Page<TweetPrediction> getAll(Pageable pageable) {
        return tweetPredictionsDao.findAll(pageable);
    }

    public TweetPrediction getById(Long id) {
        return tweetPredictionsDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException(TWEET_PREDICTION_ENTITY, id));
    }

    public void removeById(Long id) {
        TweetPrediction tweetPrediction = getById(id);
        tweetPredictionsDao.delete(tweetPrediction);
    }
}
