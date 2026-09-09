package dev.parkflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe principal da aplicação ParkFlow.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "dev.parkflow.repositories")
public class ParkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkflowApplication.class, args);
	}
}
