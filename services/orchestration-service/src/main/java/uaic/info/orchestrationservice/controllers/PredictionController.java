package uaic.info.orchestrationservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uaic.info.orchestrationservice.dto.TweetPredictionDto;
import uaic.info.orchestrationservice.multipart.PredictionRequest;
import uaic.info.orchestrationservice.services.JwtService;
import uaic.info.orchestrationservice.services.PredictionService;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/v1/predict")
@RequiredArgsConstructor
public class PredictionController {
    private final PredictionService predictionService;
    private final JwtService jwtService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA})
    public TweetPredictionDto predict(@RequestHeader("Authorization") String bearer, @ModelAttribute PredictionRequest body) throws IOException {
        log.info("POST OrchestrationController /predict");
        jwtService.ensureValid(bearer);
        final Long userId = jwtService.extractTwitterId(bearer);
        log.info(String.format("Tweet prediction request from %d", userId));
        return predictionService.predict(body, userId);
    }
}
