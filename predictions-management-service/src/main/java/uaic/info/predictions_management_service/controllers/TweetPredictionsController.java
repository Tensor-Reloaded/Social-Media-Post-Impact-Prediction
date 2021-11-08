package uaic.info.predictions_management_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uaic.info.predictions_management_service.entities.TweetPrediction;
import uaic.info.predictions_management_service.services.TweetPredictionsService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/v1/predictions")
@RequiredArgsConstructor
public class TweetPredictionsController {
    private final TweetPredictionsService tweetPredictionsService;

    @GetMapping
    public List<TweetPrediction> getAll(Long userId) {
        return tweetPredictionsService.getAllByUserId(userId);
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
