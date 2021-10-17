package uaic.info.predictions_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.predictions_management_service.daos.TweetPredictionsDao;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TweetPredictionsService {
    private static final String TWEET_PREDICTION_ENTITY = "TweetPrediction";
    private final TweetPredictionsDao tweetPredictionsDao;
    private final UserService userService;
    private final TwitterService twitterService; // facade for Twitter API

    public List<TweetPrediction> getAllByUserId(Long userId) {
        userService.checkIfExists(userId);

        return tweetPredictionsDao.findAll().stream()
                .filter(prediction -> prediction.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public TweetPrediction getById(Long id) {
        // check if the current user owns the tweet
        return tweetPredictionsDao.findById(id).orElseThrow(() ->
                new EntityNotFoundException(TWEET_PREDICTION_ENTITY, id));
    }

    public void removeById(Long id) {
        TweetPrediction tweetPrediction = getById(id);
        // check if the current user owns the tweet
        tweetPredictionsDao.delete(tweetPrediction);
    }
}
