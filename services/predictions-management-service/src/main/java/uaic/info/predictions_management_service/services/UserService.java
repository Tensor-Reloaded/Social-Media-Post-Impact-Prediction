package uaic.info.predictions_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.predictions_management_service.repositories.UsersRepository;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.entities.User;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;
import uaic.info.predictions_management_service.exceptions.UserDoesNotOwnThePredictionException;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_ENTITY = "User";
    private final UsersRepository usersRepository;


    public void createIfNotExists(Long id) {
        final User user = new User();
        user.setId(id);
        user.setPredictions(new LinkedList<>());
        usersRepository.save(user);
    }

    public void checkIfExists(Long id) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(USER_ENTITY, id);
        }
    }

    public void checkIfUserOwnsThePrediction(Long userId, Long predictionId) {
        checkIfExists(userId);
        User user = usersRepository.findById(userId).get();
        for (TweetPrediction prediction : user.getPredictions()) {
            if (prediction.getId().equals(predictionId)) {
                return;
            }
        }
        throw new UserDoesNotOwnThePredictionException(userId, predictionId);
    }
}
