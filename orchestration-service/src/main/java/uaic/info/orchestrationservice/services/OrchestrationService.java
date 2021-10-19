package uaic.info.orchestrationservice.services;

import uaic.info.orchestrationservice.clients.AccountManagementClient;
import uaic.info.orchestrationservice.clients.PredictionManagementClient;

public class OrchestrationService {
    public static class OrchestrationServiceBuilder {
        private AccountManagementClient accountManagementClient;
        private PredictionManagementClient predictionManagementClient;
        private PredictionService predictionService;


        public OrchestrationServiceBuilder setAccountManagementClient(AccountManagementClient accountManagementClient) {
            this.accountManagementClient = accountManagementClient;
            return this;
        }

        public OrchestrationServiceBuilder setPredictionManagementClient(PredictionManagementClient predictionManagementClient) {
            this.predictionManagementClient = predictionManagementClient;
            return this;
        }

        public OrchestrationServiceBuilder setPredictionService(PredictionService predictionService) {
            this.predictionService = predictionService;
            return this;
        }

        public OrchestrationService build() {
            return new OrchestrationService(
                    accountManagementClient,
                    predictionManagementClient,
                    predictionService
            );
        }
    }

    private AccountManagementClient accountManagementClient;
    private PredictionManagementClient predictionManagementClient;
    private PredictionService predictionService;

    private OrchestrationService(AccountManagementClient accountManagementClient,
                                 PredictionManagementClient predictionManagementClient,
                                 PredictionService predictionService) {
        this.accountManagementClient = accountManagementClient;
        this.predictionManagementClient = predictionManagementClient;
        this.predictionService = predictionService;
    }

}
