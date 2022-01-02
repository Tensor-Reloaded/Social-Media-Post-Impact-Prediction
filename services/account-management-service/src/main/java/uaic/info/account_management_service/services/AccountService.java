package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.exceptions.EntityNotFoundException;
import uaic.info.account_management_service.repositories.AccountRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private static final String ACCOUNT_ENTITY = "Account";
    private final AccountRepository accountRepository;

    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ACCOUNT_ENTITY, id));
    }

    public void removeById(Long id) {
        if(accountRepository.existsById(id))
            accountRepository.deleteById(id);
    }

    public void createNewAccount(Account account) {
        accountRepository.save(account);
    }

    public Optional<Account> getByTwitterId(Long twitterId) {
        return accountRepository.findById(twitterId);
    }
}
