package uaic.info.orchestrationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uaic.info.orchestrationservice.dto.PredictionRequestDto;
import uaic.info.orchestrationservice.dto.PredictionResponseDto;

@FeignClient("prediction")
public interface PredictionClient {
    @RequestMapping(method = RequestMethod.POST, value = "/predict")
    PredictionResponseDto predict(@RequestBody PredictionRequestDto tweetData);
}
