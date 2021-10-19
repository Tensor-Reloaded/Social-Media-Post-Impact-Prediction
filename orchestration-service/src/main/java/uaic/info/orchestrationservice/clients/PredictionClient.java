package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("prediction")
public interface PredictionClient {

    @RequestMapping(method = RequestMethod.POST, value = "/predict")
    Integer predict();
}
