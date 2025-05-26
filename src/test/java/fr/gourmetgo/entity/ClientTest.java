package fr.gourmetgo.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testConstructeurEtHeritage() {
        // Arrange
        String nom = "Smith";
        String prenom = "Jane";
        String email = "jane.smith@example.com";
        String motDePasse = "securePassword456";

        // Act
        Client client = new Client(nom, prenom, email, motDePasse);

        // Assert
        assertEquals(nom, client.getNom());
        assertEquals(prenom, client.getPrenom());
        assertEquals(email, client.getEmail());
        assertEquals(motDePasse, client.getMotDePasse());
        assertEquals("CLIENT", client.getTypeUtilisateur());
    }

    @Test
    public void testEqualsEtHashCode() {
        // Arrange
        Client client1 = new Client("Smith", "Jane", "jane.smith@example.com", "securePassword456");
        Client client2 = new Client("Smith", "Jane", "jane.smith@example.com", "securePassword456");
        Client client3 = new Client("Doe", "John", "john.doe@example.com", "anotherPassword123");

        // Assert
        assertEquals(client1, client2);
        assertNotEquals(client1, client3);

        assertEquals(client1.hashCode(), client2.hashCode());
        assertNotEquals(client1.hashCode(), client3.hashCode());
    }

    @Test
    public void testGetIdEtSetId() {
        // Arrange
        Client client = new Client("Smith", "Jane", "jane.smith@example.com", "securePassword456");
        Long id = 1L;

        // Act
        client.setId(id);

        // Assert
        assertEquals(id, client.getId());
    }
}
