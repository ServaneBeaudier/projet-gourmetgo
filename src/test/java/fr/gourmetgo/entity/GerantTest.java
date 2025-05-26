package fr.gourmetgo.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GerantTest {

    @Test
    public void testConstructeurEtHeritage() {
        // Arrange
        String nom = "Johnson";
        String prenom = "Mike";
        String email = "mike.johnson@example.com";
        String motDePasse = "securePassword789";

        // Act
        Gerant gerant = new Gerant(nom, prenom, email, motDePasse);

        // Assert
        assertEquals(nom, gerant.getNom());
        assertEquals(prenom, gerant.getPrenom());
        assertEquals(email, gerant.getEmail());
        assertEquals(motDePasse, gerant.getMotDePasse());
        assertEquals("GERANT", gerant.getTypeUtilisateur());
    }

    @Test
    public void testEqualsEtHashCode() {
        // Arrange
        Gerant gerant1 = new Gerant("Johnson", "Mike", "mike.johnson@example.com", "securePassword789");
        Gerant gerant2 = new Gerant("Johnson", "Mike", "mike.johnson@example.com", "securePassword789");
        Gerant gerant3 = new Gerant("Smith", "Jane", "jane.smith@example.com", "anotherPassword456");

        // Assert
        assertEquals(gerant1, gerant2);
        assertNotEquals(gerant1, gerant3);

        assertEquals(gerant1.hashCode(), gerant2.hashCode());
        assertNotEquals(gerant1.hashCode(), gerant3.hashCode());
    }

    @Test
    public void testGetIdEtSetId() {
        // Arrange
        Gerant gerant = new Gerant("Johnson", "Mike", "mike.johnson@example.com", "securePassword789");
        Long id = 1L;

        // Act
        gerant.setId(id);

        // Assert
        assertEquals(id, gerant.getId());
    }
}
