package fr.gourmetgo.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateurTest {

    @Test
    public void testEqualsEtHashCode() {
        // Arrange
        Utilisateur user1 = new Administrateur("Doe", "John", "john.doe@example.com", "securePassword123");
        Utilisateur user2 = new Administrateur("Doe", "John", "john.doe@example.com", "securePassword123");
        Utilisateur user3 = new Gerant("Smith", "Jane", "jane.smith@example.com", "anotherPassword456");

        // Assert
        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    public void testGetTypeUtilisateur() {
        // Arrange
        Utilisateur admin = new Administrateur("Doe", "John", "john.doe@example.com", "securePassword123");
        Utilisateur gerant = new Gerant("Smith", "Jane", "jane.smith@example.com", "anotherPassword456");
        Utilisateur client = new Client("Johnson", "Mike", "mike.johnson@example.com", "securePassword789");

        // Assert
        assertEquals("ADMIN", admin.getTypeUtilisateur());
        assertEquals("GERANT", gerant.getTypeUtilisateur());
        assertEquals("CLIENT", client.getTypeUtilisateur());
    }

    @Test
    public void testGettersEtSetters() {
        // Arrange
        Utilisateur user = new Administrateur();
        user.setNom("Doe");
        user.setPrenom("John");
        user.setEmail("john.doe@example.com");
        user.setMotDePasse("securePassword123");

        // Assert
        assertEquals("Doe", user.getNom());
        assertEquals("John", user.getPrenom());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("securePassword123", user.getMotDePasse());
    }
}
