package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;
import uaic.info.account_management_service.dto.BearerToken;
import uaic.info.account_management_service.dto.RedirectURL;
import uaic.info.account_management_service.entities.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwitterService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final Map<String, RequestToken> requestTokenMap = new HashMap<>();

    @Autowired
    private Twitter twitter;

    @Value("${redirectURL}")
    private String applicationRedirectURL;


    public BearerToken exchangeForBearer(String requestTokenString, String verifier) throws TwitterException {
        log.info("Generating Bearer Token");
        final var requestToken = requestTokenMap.get(requestTokenString);
        final var accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
        log.info("Successfully generated bearer token");
        return new BearerToken(jwtService.generate(accessToken.getUserId()));
    }


    public RedirectURL generateRedirectURL() throws TwitterException {
        log.info("Generating Redirect URL");
        final RequestToken requestToken = twitter.getOAuthRequestToken(applicationRedirectURL);
        requestTokenMap.put(requestToken.getToken(), requestToken);
        log.info("Successfully generated " + requestToken.getAuthorizationURL());
        return new RedirectURL(requestToken.getAuthorizationURL());
    }

    private void ensureUserExists(twitter4j.auth.AccessToken accessToken) {
        Long twitterId = accessToken.getUserId();
        String twitterName = accessToken.getScreenName();
        Optional<Account> queryResponse = accountService.getByTwitterId(twitterId);
        if (queryResponse.isEmpty()) {
            Account account = new Account();
            account.setTwitterId(twitterId);
            account.setTwitterUsername(twitterName);
            accountService.createNewAccount(account);
        }
    }
}
