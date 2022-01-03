package uaic.info.predictions_management_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.services.JwtService;
import uaic.info.predictions_management_service.services.TweetPredictionsService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/predictions")
@RequiredArgsConstructor
public class TweetPredictionsController {
    private final TweetPredictionsService tweetPredictionsService;
    private final JwtService jwtService;

    @GetMapping
    public List<TweetPrediction> getAll(@RequestHeader(name = "Authorization") String bearer) {
        log.info(String.format("GET /api/v1/predictions with token %s", bearer));
        jwtService.ensureValid(bearer);
        log.info("The provided token is valid");
        final Long twitterID = jwtService.extractTwitterId(bearer);
        log.info(String.format("Received request from twitterID %s", twitterID.toString()));
        return List.of();
    }

    @GetMapping("/{id}")
    public TweetPrediction getById(@PathVariable @Valid @Min(0) Long id, Long userId) {
        return tweetPredictionsService.getById(id, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable @Valid @Min(0) Long id, Long userId) {
        tweetPredictionsService.removeById(id, userId);
    }
}
