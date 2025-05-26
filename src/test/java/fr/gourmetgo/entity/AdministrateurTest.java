package fr.gourmetgo.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdministrateurTest {

    @Test
    public void testConstructeurEtHeritage() {
        // Arrange
        String nom = "Doe";
        String prenom = "John";
        String email = "john.doe@example.com";
        String motDePasse = "securePassword123";

        // Act
        Administrateur administrateur = new Administrateur(nom, prenom, email, motDePasse);

        // Assert
        assertEquals(nom, administrateur.getNom());
        assertEquals(prenom, administrateur.getPrenom());
        assertEquals(email, administrateur.getEmail());
        assertEquals(motDePasse, administrateur.getMotDePasse());
        assertEquals("ADMIN", administrateur.getTypeUtilisateur());
    }

    @Test
    public void testEqualsEtHashCode() {
        // Arrange
        Administrateur admin1 = new Administrateur("Doe", "John", "john.doe@example.com", "securePassword123");
        Administrateur admin2 = new Administrateur("Doe", "John", "john.doe@example.com", "securePassword123");
        Administrateur admin3 = new Administrateur("Smith", "Jane", "jane.smith@example.com", "anotherPassword456");

        // Assert
        assertEquals(admin1, admin2);
        assertNotEquals(admin1, admin3);

        assertEquals(admin1.hashCode(), admin2.hashCode());
        assertNotEquals(admin1.hashCode(), admin3.hashCode());
    }
}
