package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uaic.info.orchestrationservice.entities.TweetPrediction;

import java.util.List;

@FeignClient("prediction-management")
public interface PredictionManagementClient {
    @RequestMapping(method = RequestMethod.GET, value = "api/v1/predictions")
    List<TweetPrediction> getAll();

    @RequestMapping(method = RequestMethod.GET, value = "api/v1/predictions/{id}")
    TweetPrediction getById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.DELETE, value = "api/v1/predictions/{id}")
    Void removeById(@PathVariable("id") Long id);
}
