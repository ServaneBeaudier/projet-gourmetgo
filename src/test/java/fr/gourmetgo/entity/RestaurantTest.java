package fr.gourmetgo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class RestaurantTest {

    @Test
    public void testRestaurantGetterAndSetter(){
        Restaurant restaurant = new Restaurant("adresse", "Chez Dede", "de 10h à 12h", 0250505050, "Bistrot");

        assertEquals("adresse", restaurant.getAdresse());
        assertEquals("Chez Dede", restaurant.getNomResto());
        assertEquals("de 10h à 12h", restaurant.getHoraire());
        assertEquals(0250505050, restaurant.getNumeroTel());
        assertEquals("Bistrot", restaurant.getTypeResto());

        restaurant.setAdresse("nouvelle adresse");
        restaurant.setNomResto("chez Patou");
        restaurant.setHoraire("de 11h à 13h");
        restaurant.setNumeroTel(0251515151);
        restaurant.setTypeResto("Asiatique");

        assertEquals("nouvelle adresse", restaurant.getAdresse());
        assertEquals("chez Patou", restaurant.getNomResto());
        assertEquals("de 11h à 13h", restaurant.getHoraire());
        assertEquals(0251515151, restaurant.getNumeroTel());
        assertEquals("Asiatique", restaurant.getTypeResto());
    }

    @Test
    public void testRestaurantWithGerant() {
        // Arrange
        Gerant gerant = new Gerant();
        Restaurant restaurant = new Restaurant("adresse", "Chez Dede", "de 10h à 12h", 0250505050, "Bistrot");

        // Configure the relationship
        restaurant.setGerant(gerant);

        // Act and Assert
        assertNotNull(restaurant.getGerant());
        assertEquals(gerant, restaurant.getGerant());
    }

}
