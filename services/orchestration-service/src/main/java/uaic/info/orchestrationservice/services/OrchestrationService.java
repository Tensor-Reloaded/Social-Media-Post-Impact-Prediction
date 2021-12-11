package uaic.info.orchestrationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.orchestrationservice.clients.AccountManagementClient;
import uaic.info.orchestrationservice.clients.PredictionManagementClient;
import uaic.info.orchestrationservice.dto.GetAccessTokenRequestBody;
import uaic.info.orchestrationservice.dto.TwitterAccessToken;
import uaic.info.orchestrationservice.dto.TwitterRequestToken;

@Service
@RequiredArgsConstructor
public class OrchestrationService {
    private final AccountManagementClient accountManagementClient;
    private final PredictionManagementClient predictionManagementClient;
    private final PredictionService predictionService;

    public TwitterRequestToken getRequestToken() {
        return accountManagementClient.getRequestToken();
    }

    public TwitterAccessToken getAccessToken(GetAccessTokenRequestBody body) {
        return accountManagementClient.getAccessToken(body);
    }
}
