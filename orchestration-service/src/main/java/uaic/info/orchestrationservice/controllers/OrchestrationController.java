package uaic.info.orchestrationservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.orchestrationservice.entities.Tweet;
import uaic.info.orchestrationservice.entities.TweetPrediction;
import uaic.info.orchestrationservice.services.OrchestrationService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class OrchestrationController {
    final private OrchestrationService orchestrationService;

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
