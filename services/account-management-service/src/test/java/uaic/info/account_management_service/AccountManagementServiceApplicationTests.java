package uaic.info.account_management_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AccountManagementServiceApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }
}
