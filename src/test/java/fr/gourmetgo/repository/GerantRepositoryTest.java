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

import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.service.PasswordService;

@DataJpaTest
@Import(GerantRepositoryTest.Config.class)
@EnableJpaRepositories(basePackages = "fr.gourmetgo.repository")
@EntityScan(basePackages = "fr.gourmetgo.entity")
public class GerantRepositoryTest {

    @Autowired
    private GerantRepository gerantRepository;

    @Configuration
    static class Config {
        @Bean
        public PasswordService passwordService() {
            return mock(PasswordService.class);
        }
    }

    @Test
    void testSaveAndFindById() {
        Gerant gerant = new Gerant();
        gerant.setEmail("testgerant@example.com");
        gerant.setMotDePasse("secret");
        gerant.setNom("Martin");
        gerant.setPrenom("Alice");

        Gerant savedGerant = gerantRepository.save(gerant);

        // Vérification que l'ID a bien été générée
        assertThat(savedGerant.getId()).isNotNull();

        Optional<Gerant> found = gerantRepository.findById(savedGerant.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("testgerant@example.com");
        assertThat(found.get().getNom()).isEqualTo("Martin");
        assertThat(found.get().getPrenom()).isEqualTo("Alice");
    }

    @Test
void testFindByEmail() {
    Gerant gerant = new Gerant();
    gerant.setEmail("findme@example.com");
    gerant.setMotDePasse("motdepasse");
    gerant.setNom("Lemoine");
    gerant.setPrenom("Sophie");

    gerantRepository.save(gerant);

    Optional<Gerant> found = gerantRepository.findByEmail("findme@example.com");

    assertThat(found).isPresent();
    assertThat(found.get().getNom()).isEqualTo("Lemoine");
    assertThat(found.get().getPrenom()).isEqualTo("Sophie");
}

}
