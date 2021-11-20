package uaic.info.orchestrationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.orchestrationservice.entities.TweetPrediction;
import uaic.info.orchestrationservice.services.OrchestrationService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class OrchestrationController {
    private final OrchestrationService orchestrationService;

    @GetMapping("/authorize")
    public void authorize() {
    }

    @GetMapping("/prediction")
    public List<TweetPrediction> getAllPredictions() {
        return null;
    }

    @GetMapping("/prediction/{id}")
    public TweetPrediction getPrediction(@PathVariable("id") String tweetId) {
        return null;
    }

    @DeleteMapping("/prediction/{id}")
    public void deletePrediction(@PathVariable("id") String tweetId) {

    }

    @PostMapping("/prediction")
    public TweetPrediction createPrediction() {
        return null;
    }
}
