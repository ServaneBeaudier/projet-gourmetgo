package fr.gourmetgo;

import fr.gourmetgo.entite.Restaurant;
import fr.gourmetgo.repository.AppRepository;
import fr.gourmetgo.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjetGourmetgoApplicationTests {

    @Autowired
    private RestaurantService restaurantService; 
    @MockBean
    private AppRepository appRepository; // Crée un faux repository pour les tests

    @Test
    void testEnregistrementRestaurant() {
        Restaurant resto = new Restaurant();
        resto.setNomResto("Chez Dédé");
        resto.setNumRue("12");
        resto.setNomRue("Rue de la Paix");
        resto.setVille("Paris");
        resto.setCodePostal("75002");          
        resto.setTelResto("0159897451");
        resto.setTypeResto("Italien");

        assertDoesNotThrow(() -> restaurantService.enregistrerRestaurant(resto)); //Vérifie qu’il n’y a pas d’erreur
    }
}
