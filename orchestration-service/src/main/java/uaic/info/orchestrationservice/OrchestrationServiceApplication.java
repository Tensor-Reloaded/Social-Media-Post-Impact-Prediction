package uaic.info.orchestrationservice;

import feign.Feign;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableEurekaServer
@EnableFeignClients
public class OrchestrationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServiceApplication.class, args);
	}
}
