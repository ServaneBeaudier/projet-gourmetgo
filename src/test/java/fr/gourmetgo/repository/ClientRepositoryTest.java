package fr.gourmetgo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import static org.mockito.Mockito.mock;

import fr.gourmetgo.entity.Client;
import fr.gourmetgo.service.PasswordService;

@DataJpaTest
@Import(ClientRepositoryTest.Config.class)
@EnableJpaRepositories(basePackages = "fr.gourmetgo.repository")
@EntityScan(basePackages = "fr.gourmetgo.entity")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Configuration
    static class Config {
        @Bean
        public PasswordService passwordService() {
            return mock(PasswordService.class);
        }
    }

    @Test
void testSaveAndFindById() {
    Client client = new Client();
    client.setEmail("testclient@example.com");
    client.setMotDePasse("secret");
    client.setNom("Dupont");
    client.setPrenom("Jean");

    Client savedClient = clientRepository.save(client);

    // Vérifie que l'ID a bien été généré par JPA
    System.out.println("ID généré = " + savedClient.getId());
    assertThat(savedClient.getId()).isNotNull(); // <-- Test ajouté ici

    Optional<Client> found = clientRepository.findById(savedClient.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getEmail()).isEqualTo("testclient@example.com");
    assertThat(found.get().getNom()).isEqualTo("Dupont");
    assertThat(found.get().getPrenom()).isEqualTo("Jean");
}

}
