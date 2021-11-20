package uaic.info.predictions_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;
import uaic.info.predictions_management_service.daos.UsersRepository;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.entities.User;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;
import uaic.info.predictions_management_service.exceptions.UserDoesNotOwnThePredictionException;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_ENTITY = "User";
    private final UsersRepository usersRepository;
    private final TwitterService twitterService;

    public void checkIfExists(Long id) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(USER_ENTITY, id);
        }
    }

    public void checkIfUserOwnsThePrediction(Long userId, Long predictionId) {
        User user = usersRepository.findById(userId).get();
        for (TweetPrediction prediction : user.getPredictions()) {
            if (prediction.getId().equals(predictionId)) {
                return;
            }
        }
        throw new UserDoesNotOwnThePredictionException(userId, predictionId);
    }

    public boolean doesUserOwnsTweet(Long userId, Long tweetId) throws TwitterException {
        checkIfExists(userId);
        final Long tweetOwnerId = twitterService.getTweetById(tweetId)
                .getUser()
                .getId();
        return tweetOwnerId.equals(userId);
    }
}
