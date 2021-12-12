package uaic.info.orchestrationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import uaic.info.orchestrationservice.clients.AccountManagementClient;
import uaic.info.orchestrationservice.clients.PredictionManagementClient;
import uaic.info.orchestrationservice.dto.GetAccessTokenRequestBody;
import uaic.info.orchestrationservice.dto.TwitterAccessToken;
import uaic.info.orchestrationservice.dto.TwitterRequestToken;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrchestrationService {
    private final AccountManagementClient accountManagementClient;
    private final PredictionManagementClient predictionManagementClient;
    private final PredictionService predictionService;
    private final DiscoveryClient discoveryClient;

    public TwitterRequestToken getRequestToken() {
        return accountManagementClient.getRequestToken();
    }

    public TwitterAccessToken getAccessToken(GetAccessTokenRequestBody body) {
        return accountManagementClient.getAccessToken(body);
    }

    public List<ServiceInstance> getInstances(String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }
}
