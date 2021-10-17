package uaic.info.predictions_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.predictions_management_service.daos.UsersDao;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_ENTITY = "User";
    private final UsersDao usersDao;

    public void checkIfExists(Long id) {
        if (usersDao.findById(id).isEmpty()) {
            throw new EntityNotFoundException(USER_ENTITY, id);
        }
    }

    public boolean doesUserOwnsTweet(Long userId, Long tweetId) {
        return true;
    }
}
