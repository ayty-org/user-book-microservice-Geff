package br.com.phoebus.microservice.biblioteca.userandbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserandbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserandbookApplication.class, args);
	}

}
