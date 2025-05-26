package fr.gourmetgo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.gourmetgo.entity.Administrateur;
import fr.gourmetgo.entity.Client;
import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.AuthRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

@Bean
CommandLineRunner init(AuthRepository authRepository) {
return args -> {
	// authRepository.save(new Administrateur("ad", "min", "admin@gg.com", "admin123"));
	// authRepository.save(new Gerant("ge", "rant", "dede@gg.com", "dede123"));
	// authRepository.save(new Client("cl", "ient", "vincent@gg.com", "vincent123"));
	};
}

}