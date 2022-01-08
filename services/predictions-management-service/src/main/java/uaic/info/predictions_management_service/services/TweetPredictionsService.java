package uaic.info.predictions_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.predictions_management_service.dto.TweetPredictionDto;
import uaic.info.predictions_management_service.entities.User;
import uaic.info.predictions_management_service.repositories.TweetPredictionsRepository;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;
import uaic.info.predictions_management_service.repositories.UsersRepository;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TweetPredictionsService {
    private static final String TWEET_PREDICTION_ENTITY = "TweetPrediction";
    private final TweetPredictionsRepository tweetPredictionsRepository;
    private final UsersRepository usersRepository;
    private final UserService userService;

    @Transactional
    public void createPrediction(TweetPredictionDto req) {
        userService.createIfNotExists(req.getUserId());
        final User user = usersRepository.findById(req.getUserId()).get();
        final TweetPrediction tweetPrediction = new TweetPrediction();
        tweetPrediction.setTweetText(req.getTweetText());
        tweetPrediction.setImageData(req.getImageData().getBytes(StandardCharsets.UTF_8));
        tweetPrediction.setPredictedNumberOfLikes(req.getPredictedNumberOfLikes());
        tweetPrediction.setUser(user);
        tweetPredictionsRepository.save(tweetPrediction);
        user.getPredictions().add(tweetPrediction);
        usersRepository.save(user);
    }

    public List<TweetPredictionDto> getAllByUserId(Long userId) {
        userService.createIfNotExists(userId);

        return tweetPredictionsRepository.findAll().stream()
                .filter(prediction -> prediction.getUser().getId().equals(userId)) //TODO: Null pointer here
                .map(TweetPredictionDto::of)
                .collect(Collectors.toList());
    }

    public TweetPrediction getById(Long id, Long userId) {
        userService.checkIfExists(userId);
        userService.checkIfUserOwnsThePrediction(userId, id);

        return tweetPredictionsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(TWEET_PREDICTION_ENTITY, id));
    }

    public void removeById(Long id, Long userId) {
        userService.checkIfExists(userId);
        final TweetPrediction tweetPrediction = getById(id, userId);
        final User user = tweetPrediction.getUser();
        user.getPredictions().remove(tweetPrediction);
        tweetPredictionsRepository.delete(tweetPrediction);
        usersRepository.save(user);
    }
}
