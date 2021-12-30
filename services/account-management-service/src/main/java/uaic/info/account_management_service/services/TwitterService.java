package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
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


@Service
@RequiredArgsConstructor
public class TwitterService {
    private final AccountService accountService;
    private final JwtService jwtService;
    private final Map<String, RequestToken> requestTokenMap = new HashMap<>();

    @Value("${redirectURL}")
    private String applicationRedirectURL;


    public BearerToken exchangeForBearer(String requestTokenString, String verifier) throws TwitterException {
        final Twitter twitter = getTwitter();
        assert twitter != null;
        final var requestToken = requestTokenMap.get(requestTokenString);
        final var accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
        return new BearerToken(jwtService.generate(accessToken.getUserId()));
    }


    public RedirectURL generateRedirectURL() throws TwitterException {
        final Twitter twitter = getTwitter();
        assert twitter != null;
        final RequestToken requestToken = twitter.getOAuthRequestToken(applicationRedirectURL);
        requestTokenMap.put(requestToken.getToken(), requestToken);
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

    @Lookup
    private Twitter getTwitter() {
        return null;
    }
}
