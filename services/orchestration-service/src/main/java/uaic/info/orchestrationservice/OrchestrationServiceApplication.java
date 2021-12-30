package uaic.info.orchestrationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableZuulProxy
public class OrchestrationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrchestrationServiceApplication.class, args);
	}
}
