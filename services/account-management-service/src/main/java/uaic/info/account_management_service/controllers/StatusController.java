package uaic.info.account_management_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;
import uaic.info.account_management_service.dto.PostDto;
import uaic.info.account_management_service.services.JwtService;
import uaic.info.account_management_service.services.TwitterService;

@Slf4j
@RestController
@RequestMapping("/api/v1/status")
@RequiredArgsConstructor
public class StatusController {
    private final TwitterService twitterService;
    private final JwtService jwtService;

    @PostMapping
    public void updateStatus(@RequestHeader("Authorization") String bearer, @RequestBody PostDto tweetPredictionDto) throws TwitterException {
        log.info("POST /status");
        jwtService.ensureValid(bearer);
        final Long twitterId = jwtService.extractTwitterId(bearer);
        log.info(String.format("JWT is valid for user %d", twitterId));
        twitterService.postTweet(twitterId, tweetPredictionDto);
        log.info(String.format("Successfully posted tweet for user id %d", twitterId));
    }
}
