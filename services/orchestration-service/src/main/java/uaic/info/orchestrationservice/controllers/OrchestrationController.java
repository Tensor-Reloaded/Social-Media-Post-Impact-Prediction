package uaic.info.orchestrationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;
import uaic.info.orchestrationservice.dto.GetAccessTokenRequestBody;
import uaic.info.orchestrationservice.dto.TwitterAccessToken;
import uaic.info.orchestrationservice.dto.TwitterRequestToken;
import uaic.info.orchestrationservice.entities.TweetPrediction;
import uaic.info.orchestrationservice.services.OrchestrationService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class OrchestrationController {
    private final OrchestrationService orchestrationService;

    @GetMapping("requestToken")
    public TwitterRequestToken getRequestToken() {
        return orchestrationService.getRequestToken();
    }

    @PostMapping("accessToken")
    public TwitterAccessToken exchangeRequestToken(@RequestBody GetAccessTokenRequestBody body) {
        return orchestrationService.getAccessToken(body);
    }

    @GetMapping("prediction")
    public List<TweetPrediction> getAllPredictions() {
        return null;
    }

    @GetMapping("prediction/{id}")
    public TweetPrediction getPrediction(@PathVariable("id") String tweetId) {
        return null;
    }

    @DeleteMapping("prediction/{id}")
    public void deletePrediction(@PathVariable("id") String tweetId) {

    }

    @PostMapping("prediction")
    public TweetPrediction createPrediction() {
        return null;
    }

    @GetMapping("services/{svc}")
    public List<ServiceInstance> getInstances(@PathVariable("svc") String serviceName) {
        return orchestrationService.getInstances(serviceName);
    }

}
