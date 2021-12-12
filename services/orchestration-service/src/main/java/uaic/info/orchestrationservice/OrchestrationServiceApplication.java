package uaic.info.orchestrationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class OrchestrationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServiceApplication.class, args);
	}
}
