package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uaic.info.orchestrationservice.dto.TweetPredictionDto;

import java.util.List;

@FeignClient("prediction-management")
public interface PredictionManagementClient {
    @RequestMapping(method = RequestMethod.POST, path = "/api/v1/predictions")
    void createPrediction(@RequestBody TweetPredictionDto tweetPredictionDto);
}
