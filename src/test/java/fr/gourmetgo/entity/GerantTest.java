/* package fr.gourmetgo.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class GerantTest {

    @Test
    public void testGetterAndSetter(){
        Gerant gerant = new Gerant();
        Restaurant restaurant = new Restaurant();

        gerant.setRestaurant(restaurant);
        restaurant.setGerant(gerant);

        assertNotNull(gerant.getRestaurant());
        assertEquals("Chez Dede", gerant.getRestaurant().getNomResto());

        restaurant.setNomResto("Chez Patou");

        assertEquals("Chez Patou", gerant.getRestaurant().getNomResto());
    }

}
 */