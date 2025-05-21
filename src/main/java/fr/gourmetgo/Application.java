package fr.gourmetgo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.gourmetgo.model.User;
import fr.gourmetgo.repository.UserRepository;



@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

@Bean
CommandLineRunner init(UserRepository userRepository) {
return args -> {
	userRepository.save(new User(null, "admin", "admin123"));
	userRepository.save(new User(null, "user", "pass"));
	};
}

}