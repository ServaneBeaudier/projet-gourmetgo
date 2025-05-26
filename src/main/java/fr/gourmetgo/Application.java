package fr.gourmetgo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import fr.gourmetgo.entity.Administrateur;
import fr.gourmetgo.entity.Client;
import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.AuthRepository;
import fr.gourmetgo.service.PasswordService;

/**
 * Classe principale de l'application Spring Boot.
 * Cette classe initialise et démarre l'application Spring Boot.
 */
@SpringBootApplication
public class Application {

    /**
     * Méthode principale pour démarrer l'application Spring Boot.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Initialise des utilisateurs par défaut dans la base de données lors du démarrage de l'application.
     *
     * @param authRepository Le dépôt pour sauvegarder les utilisateurs.
     * @param passwordService Le service pour hacher les mots de passe.
     * @return Un CommandLineRunner qui initialise les utilisateurs.
     */
    @Bean
    @Profile("!test")
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
