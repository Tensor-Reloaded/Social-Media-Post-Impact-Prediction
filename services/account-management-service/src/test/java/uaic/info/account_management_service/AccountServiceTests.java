package uaic.info.account_management_service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.exceptions.EntityNotFoundException;
import uaic.info.account_management_service.repositories.AccountRepository;
import uaic.info.account_management_service.services.AccountService;

import java.util.stream.LongStream;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
class AccountServiceTests {
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    private AccountService accountService;
//
//    @BeforeAll
//    void setUp() {
//        accountService = new AccountService(accountRepository);
//        LongStream.range(1, 10).mapToObj(id -> {
//            Account account = new Account();
//            account.setId(id);
//            account.setTwitterId(id);
//            account.setTwitterUsername("twitterUser");
//            return account;
//        }).forEach(accountRepository::save);
//    }
//
//    @Test
//    void testAccountThatExistsDoesNotThrowException() {
//        final Long idThatExists = 1L;
//        Assertions.assertDoesNotThrow(() -> accountService.getById(idThatExists));
//    }
//
//    @Test
//    void testAccountThatDoesNotExistThrowsException() {
//        final Long idThatDoesntExist = -1L;
//        Assertions.assertThrows(EntityNotFoundException.class, () -> accountService.getById(idThatDoesntExist));
//    }
//
//    @Test
//    void testDeletedAccountWillThrowExceptionWhenAttemptingToRetrieve() {
//        final Long idThatExists = 1L;
//        accountService.removeById(idThatExists);
//
//        Assertions.assertThrows(EntityNotFoundException.class, () -> accountService.getById(idThatExists));
//    }
}
