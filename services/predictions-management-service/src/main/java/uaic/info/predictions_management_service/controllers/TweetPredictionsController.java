package uaic.info.predictions_management_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uaic.info.predictions_management_service.dto.TweetPredictionDto;
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
        final Long userId = extractId(bearer);
        log.info(String.format("Getting all prerdictions for user %s", userId));
        return tweetPredictionsService.getAllByUserId(userId);
    }

    @PostMapping
    public void createPrediction(@RequestBody TweetPredictionDto body) {
        log.info(String.format("Creating tweet prediction for user with id %s", body.getUserId()));
        tweetPredictionsService.createPrediction(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@RequestHeader(name = "Authorization") String bearer, @PathVariable @Valid @Min(0) Long id) {
        final Long userId = extractId(bearer);
        tweetPredictionsService.removeById(id, userId);
        log.info(String.format("Removed user with id %s", userId.toString()));
    }

    private Long extractId(String bearer) {
        log.info(String.format("GET /api/v1/predictions with token %s", bearer));
        jwtService.ensureValid(bearer);
        log.info("The provided token is valid");
        return jwtService.extractTwitterId(bearer);
    }
}
