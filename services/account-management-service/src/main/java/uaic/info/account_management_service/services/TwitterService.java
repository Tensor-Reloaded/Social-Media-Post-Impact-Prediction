package uaic.info.account_management_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import uaic.info.account_management_service.dto.BearerToken;
import uaic.info.account_management_service.dto.RedirectURL;
import uaic.info.account_management_service.dto.PostDto;

import java.io.ByteArrayInputStream;
import java.util.Base64;
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
        log.info("Successfully generated bearer token");
        accountService.updateUser(accessToken);
        log.info(String.format("Successfully updated user credentials for %s", accessToken.getUserId()));
        return new BearerToken(jwtService.generate(accessToken.getUserId()));
    }


    public RedirectURL generateRedirectURL() throws TwitterException {
        log.info("Generating Redirect URL");
        final RequestToken requestToken = twitter.getOAuthRequestToken(applicationRedirectURL);
        requestTokenMap.put(requestToken.getToken(), requestToken);
        log.info("Successfully generated " + requestToken.getAuthorizationURL());
        return new RedirectURL(requestToken.getAuthorizationURL());
    }

    public void postTweet(Long twitterId, PostDto prediction) throws TwitterException {
        final AccessToken accessToken = accountService.fetchCredentials(twitterId);
        twitter.setOAuthAccessToken(accessToken);
        final StatusUpdate statusUpdate = new StatusUpdate(prediction.getTweetText());
        final String name = String.valueOf(prediction.hashCode()) + ".jpg";
        final byte[] contents = Base64.getDecoder().decode(prediction.getImageData());
        statusUpdate.setMedia(name, new ByteArrayInputStream(contents));
        twitter.updateStatus(statusUpdate);
    }
}
