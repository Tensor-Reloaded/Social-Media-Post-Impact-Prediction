package uaic.info.account_management_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import uaic.info.account_management_service.dto.BearerToken;
import uaic.info.account_management_service.dto.RedirectURL;
import uaic.info.account_management_service.dto.TokenExchangeRequest;
import uaic.info.account_management_service.services.TwitterService;

@Slf4j
@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final TwitterService twitterService;

    @GetMapping("/authorize")
    public RedirectURL authorize() throws TwitterException {
        log.info("GET /api/v1/oauth/authorize");
        return twitterService.generateRedirectURL();
    }

    @PostMapping("/verify")
    public BearerToken verify(@RequestBody TokenExchangeRequest req) throws TwitterException {
        log.info("POST /api/v1/oauth/verify");
        return twitterService.exchangeForBearer(req.getRequestToken(), req.getVerifier());
    }

}
