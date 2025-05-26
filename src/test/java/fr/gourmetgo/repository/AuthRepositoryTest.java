package fr.gourmetgo.repository;

import fr.gourmetgo.entity.Client;
import fr.gourmetgo.entity.Utilisateur;
import fr.gourmetgo.repository.AuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    void testFindByEmail() {
        // Crée un Client (qui hérite de Utilisateur)
        Client client = new Client();
        client.setEmail("test@example.com");
        client.setMotDePasse("password123");
        authRepository.save(client);

        Optional<Utilisateur> found = authRepository.findByEmail("test@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
    }
}
