package com.easybank.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator easyBankCustomRoute(RouteLocatorBuilder foRouteLocatorBuilder)
	{
		return foRouteLocatorBuilder.routes()
				.route(p -> p
						.path("/easybank/ACCOUNTS/**")
						.filters(f ->
								f.rewritePath("/easybank/ACCOUNTS/(?<segment>.*)","/${segment}")
										.circuitBreaker( c -> c.setName("accountsCircuit")
												.setFallbackUri("forward:/fallback/products")))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/easybank/CARDS/**")
						.filters(f ->
								f.rewritePath("/easybank/CARDS/(?<segment>.*)","/${segment}")
										.retry(re -> re.setRetries(3)
												.setMethods(HttpMethod.GET)
												.setBackoff(Duration.ofMillis(100),Duration.ofMillis(1000),2,true)))
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/easybank/LOANS/**")
						.filters(f ->
								f.rewritePath("/easybank/LOANS/(?<segment>.*)","/${segment}"))
						.uri("lb://LOANS")).build();
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(30)).build()).build());
	}
}
