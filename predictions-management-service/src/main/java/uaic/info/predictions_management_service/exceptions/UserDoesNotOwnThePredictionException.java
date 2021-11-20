package uaic.info.predictions_management_service.exceptions;

public class UserDoesNotOwnThePredictionException extends RuntimeException {
    public UserDoesNotOwnThePredictionException(Long userId, Long predictionId) {
        super(String.format("User with id %d does not own the prediction with id %d", userId, predictionId));
    }
}
