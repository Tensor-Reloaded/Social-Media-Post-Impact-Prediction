package uaic.info.account_management_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import uaic.info.account_management_service.dto.GetAccessTokenRequestBody;
import uaic.info.account_management_service.dto.TwitterAccessToken;
import uaic.info.account_management_service.dto.TwitterRequestToken;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.exceptions.InvalidTwitterRequestToken;
import uaic.info.account_management_service.services.AccountService;
import uaic.info.account_management_service.services.TwitterService;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class TwitterLoginController {

    private final TwitterService twitterService;

    private final AccountService accountService;

    private String currentRequestToken;

    @Value("${front-end.domain}")
    private String callbackUrl;

    @PostMapping("request_token")
    public TwitterRequestToken getRequestToken() throws TwitterException {
        Twitter twitter = twitterService.getTwitter();
        RequestToken requestToken = twitter.getOAuthRequestToken(callbackUrl);
        currentRequestToken = requestToken.getToken();

        return new TwitterRequestToken(requestToken.getToken());
    }

    @PostMapping("access_token")
    public TwitterAccessToken getAccessToken(@RequestBody GetAccessTokenRequestBody requestBody) throws TwitterException, InvalidTwitterRequestToken {
        if (!requestBody.getOauthToken().equals(currentRequestToken)) throw new InvalidTwitterRequestToken();
        Twitter twitter = twitterService.getTwitter();
        AccessToken accessToken = twitter.getOAuthAccessToken(requestBody.getOauthToken(),
                requestBody.getOauthVerifier());

        ensureUserExists(accessToken);
        return new TwitterAccessToken(accessToken.getToken());
    }

    private void ensureUserExists(AccessToken accessToken) {
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
