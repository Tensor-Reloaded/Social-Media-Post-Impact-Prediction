package uaic.info.account_management_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uaic.info.account_management_service.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
