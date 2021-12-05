package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uaic.info.orchestrationservice.entities.Tweet;

@FeignClient("prediction")
public interface PredictionClient {
    @RequestMapping(method = RequestMethod.POST, value = "/predict")
    Integer predict(Tweet tweet);
}
