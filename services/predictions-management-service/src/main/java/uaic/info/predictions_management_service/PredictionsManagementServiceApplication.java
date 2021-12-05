package uaic.info.predictions_management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PredictionsManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PredictionsManagementServiceApplication.class, args);
    }
}
