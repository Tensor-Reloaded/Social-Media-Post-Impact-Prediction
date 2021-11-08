package uaic.info.orchestrationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.orchestrationservice.clients.AccountManagementClient;
import uaic.info.orchestrationservice.clients.PredictionManagementClient;

@Service
@RequiredArgsConstructor
public class OrchestrationService {
    private final AccountManagementClient accountManagementClient;
    private final PredictionManagementClient predictionManagementClient;
    private final PredictionService predictionService;
}
