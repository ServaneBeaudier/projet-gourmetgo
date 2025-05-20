package fr.gourmetgo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GerantTest {

    @Test
    public void testGetterAndSetter(){
        Gerant gerant = new Gerant();
        Restaurant restaurant = new Restaurant("adresse", "Chez Dede", "de 10h à 12h", 0250505050, "Bistrot");

        gerant.setRestaurant(restaurant);
        restaurant.setGerant(gerant);

        assertNotNull(gerant.getRestaurant());
        assertEquals("Chez Dede", gerant.getRestaurant().getNomResto());

        restaurant.setNomResto("Chez Patou");

        assertEquals("Chez Patou", gerant.getRestaurant().getNomResto());
    }

}
