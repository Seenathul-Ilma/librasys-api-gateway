package lk.ijse.eca.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-service", r -> r.path("/api/users", "/api/users/**")
						.uri("lb://USER-SERVICE"))
				.route("book-service", r -> r.path("/api/books", "/api/books/**")
						.uri("lb://BOOK-SERVICE"))
				.route("loan-service", r -> r.path("/api/loans", "/api/loans/**")
						.uri("lb://LOAN-SERVICE"))
				.build();
	}
}
