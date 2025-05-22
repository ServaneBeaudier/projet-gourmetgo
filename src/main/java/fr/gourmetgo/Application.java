package fr.gourmetgo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.gourmetgo.entity.Administrateur;
import fr.gourmetgo.entity.Client;
import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.AuthRepository;
import fr.gourmetgo.service.PasswordService;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

@Bean
CommandLineRunner init(AuthRepository authRepository, PasswordService passwordService) {
    return args -> {
        System.out.println("Initializing users...");

        String hashedAdminPassword = passwordService.hashPassword("admin123");
        String hashedGerantPassword = passwordService.hashPassword("dede123");
        String hashedClientPassword = passwordService.hashPassword("vincent123");

        System.out.println("Saving admin user...");
        authRepository.save(new Administrateur("ad", "min", "admin@gg.com", hashedAdminPassword));

        System.out.println("Saving gerant user...");
        authRepository.save(new Gerant("ge", "rant", "dede@gg.com", hashedGerantPassword));

        System.out.println("Saving client user...");
        authRepository.save(new Client("cl", "ient", "vincent@gg.com", hashedClientPassword));

        System.out.println("Users initialized.");
    };
}

}