package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.auth.AccessToken;
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
        if (accountRepository.existsById(id))
            accountRepository.deleteById(id);
    }

    public Optional<Account> getByTwitterId(Long twitterId) {
        return accountRepository.findById(twitterId);
    }


    public void updateUser(AccessToken accessToken) {
        Optional<Account> queryResponse = getByTwitterId(accessToken.getUserId());
        final Account account = queryResponse.orElseGet(Account::new);
        account.setId(accessToken.getUserId());
        account.setKey(accessToken.getToken());
        account.setSecret(accessToken.getTokenSecret());
        accountRepository.save(account);
    }

    public AccessToken fetchCredentials(long twitterId) {
        final Optional<Account> account = getByTwitterId(twitterId);
        if (account.isEmpty()) {
            throw new javax.persistence.EntityNotFoundException();
        }
        if (account.get().getKey() == null || account.get().getSecret() == null) {
            throw new IllegalStateException();
        }
        return new AccessToken(account.get().getKey(), account.get().getSecret());
    }
}
