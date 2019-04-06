package com.invillia.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

/**
 * @author thiago.nvieira
 *
 * Class inicialização e configuração do Spring Boot.
 */
@SpringBootApplication
@EnableJpaRepositories("com.invillia.acme.repository")
@EnableDiscoveryClient
@EntityScan(basePackages = { "com.invillia.acme.entity" }, basePackageClasses = Jsr310JpaConverters.class)
public class PaymentApplication {
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
}
