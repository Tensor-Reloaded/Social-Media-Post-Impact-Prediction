package uaic.info.predictions_management_service.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import twitter4j.Status;
import twitter4j.TwitterException;
import uaic.info.predictions_management_service.daos.UsersDao;
import uaic.info.predictions_management_service.entities.User;
import uaic.info.predictions_management_service.exceptions.EntityNotFoundException;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
    @Mock
    private TwitterService twitterService;

    @Autowired
    private UsersDao usersDao;

    private UserService userService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(usersDao, twitterService);
        LongStream.range(0, 1000)
                .mapToObj(id -> {
                    User user = new User();
                    user.setId(id);
                    return user;
                })
                .forEach(usersDao::save);
    }

    @Test
    void testUserThatDoesntExistThrowsException() {
        final Long idThatDoesntExist = -1L;
        assertThrows(EntityNotFoundException.class, () -> {
            userService.checkIfExists(idThatDoesntExist);
        });
    }

    @Test
    void testUserThatExistsDoesntThrowException() {
        final Long idThatExists = 0L;
        assertDoesNotThrow(() -> {
            userService.checkIfExists(idThatExists);
        });
    }

    @Test
    void testDoesUserOwnsTweetHappyScenario() throws TwitterException {
        final Long userId = 0L;
        final Long tweetId = 0L;
        Status mockStatus = Mockito.mock(Status.class);
        twitter4j.User mockUser = Mockito.mock(twitter4j.User.class);
        Mockito.when(mockUser.getId()).thenReturn(userId);
        Mockito.when(mockStatus.getUser()).thenReturn(mockUser);
        Mockito.when(twitterService.getTweetById(tweetId)).thenReturn(mockStatus);
        assertTrue(userService.doesUserOwnsTweet(userId, tweetId));
    }

    @Test
    void testDoesUserOwnsTweetUnhappyScenario() throws TwitterException {
        final Long userId = 1L;
        final Long trueUserId = 0L;
        final Long tweetId = 0L;
        Status mockStatus = Mockito.mock(Status.class);
        twitter4j.User mockUser = Mockito.mock(twitter4j.User.class);
        Mockito.when(mockUser.getId()).thenReturn(trueUserId);
        Mockito.when(mockStatus.getUser()).thenReturn(mockUser);
        Mockito.when(twitterService.getTweetById(tweetId)).thenReturn(mockStatus);
        assertFalse(userService.doesUserOwnsTweet(userId, tweetId));
    }
}