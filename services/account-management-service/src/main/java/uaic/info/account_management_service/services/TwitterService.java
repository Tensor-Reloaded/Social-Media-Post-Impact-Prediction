package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;
import uaic.info.account_management_service.dto.BearerToken;
import uaic.info.account_management_service.dto.RedirectURL;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.exceptions.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

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
        ensureUserExists(accessToken);
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
        try {
            Account queryResponse = accountService.getById(twitterId);
            queryResponse.setKey(accessToken.getToken());
            queryResponse.setSecret(accessToken.getTokenSecret());
            accountService.createUpdateAccount(queryResponse);
            log.info("Updated account's access token and secret");
        } catch (EntityNotFoundException exception) {
            Account account = new Account();
            account.setId(twitterId);
            account.setKey(accessToken.getToken());
            account.setSecret(accessToken.getTokenSecret());
            accountService.createUpdateAccount(account);
            log.info("Created account with access token and secret");
        }
    }
}
