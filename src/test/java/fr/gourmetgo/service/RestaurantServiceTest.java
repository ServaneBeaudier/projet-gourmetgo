package fr.gourmetgo.service;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;


//--------------------------------------------------------------------------------------------
      /* ------------------ Partie Adam (TESTS RestaurantService) ------------------ */
//--------------------------------------------------------------------------------------------

/*------------------------------------------------------------------------------------------------------------------------------------------------- */
    /**
     * Teste la méthode enregistrerRestaurant.
     * On simule ici qu'un restaurant est enregistré et qu'un identifiant est attribué.
     */
    @Test
    public void testEnregistrerRestaurant() {
        // Création d'un Restaurant à enregistrer
        Restaurant input = new Restaurant();
        input.setNomResto("Test Restaurant");
        input.setNumRue("123");
        input.setNomRue("Rue Test");
        input.setCodePostal("75001");
        input.setVille("Paris");
        input.setTelResto("0123456789");
        input.setTypeResto("Sushi");
        input.setHeureOuverture(LocalTime.of(9, 0));
        input.setHeureFermeture(LocalTime.of(22, 0));
        
        // Création d'un objet simulé renvoyé par le repository avec un ID
        Restaurant saved = new Restaurant();
        saved.setId(1L);
        saved.setNomResto("Test Restaurant");
        saved.setNumRue("123");
        saved.setNomRue("Rue Test");
        saved.setCodePostal("75001");
        saved.setVille("Paris");
        saved.setTelResto("0123456789");
        saved.setTypeResto("Sushi");
        saved.setHeureOuverture(LocalTime.of(9, 0));
        saved.setHeureFermeture(LocalTime.of(22, 0));
        
        // Simuler le comportement du repository lors de l'appel de save()
        when(restaurantRepository.save(input)).thenReturn(saved);
        
        // Appel de la méthode à tester
        Restaurant result = restaurantService.enregistrerRestaurant(input);
        
        // Vérification : l'objet retourné doit avoir l'ID assigné et les autres valeurs doivent être identiques
        assertNotNull(result, "Le restaurant enregistré ne doit pas être null");
        assertEquals(1L, result.getId(), "L'ID du restaurant doit être 1");
        assertEquals("Test Restaurant", result.getNomResto());
        assertEquals("123", result.getNumRue());
        assertEquals("Rue Test", result.getNomRue());
        assertEquals("75001", result.getCodePostal());  
        assertEquals("Paris", result.getVille());
        assertEquals("0123456789", result.getTelResto());
        assertEquals("Sushi", result.getTypeResto());
        assertEquals(LocalTime.of(9, 0), result.getHeureOuverture());
        assertEquals(LocalTime.of(22, 0), result.getHeureFermeture());
        
        // Vérification que le repository a bien été invoqué une seule fois
        verify(restaurantRepository, times(1)).save(input);
    }
/*------------------------------------------------------------------------------------------------------------------------------------------------- */
/*------------------------------------------------------------------------------------------------------------------------------------------------- */
    /**
     * Teste la méthode restaurantExists lorsque le restaurant existe déjà.
     * On simule que l'appel du repository retourne un Optional contenant un restaurant.
     */
    @Test
    public void testRestaurantExists_ReturnsTrue() {
        //// Création d'un restaurant dont l'existence est à vérifier
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Test Restaurant");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue Test");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        
        // Simulation : le repository retourne un Optional non vide ( le restaurant existe déjà) 
        when(restaurantRepository.findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
             restaurant.getNomResto(),
             restaurant.getCodePostal(),
             restaurant.getVille(),
             restaurant.getNomRue(),
             restaurant.getNumRue()
        )).thenReturn(Optional.of(new Restaurant()));  // Un restaurant est trouvé
        
        // Appel de la méthode à tester
        boolean exists = restaurantService.restaurantExists(restaurant);
        
        // On s'attend à ce que la méthode retourne true
        assertTrue(exists, "La méthode doit retourner true car le restaurant existe");
        
        // Vérifier que le repository a été appelé avec les bons paramètres
        verify(restaurantRepository, times(1)).findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
             restaurant.getNomResto(),
             restaurant.getCodePostal(),
             restaurant.getVille(),
             restaurant.getNomRue(),
             restaurant.getNumRue()
        );

    }
/*------------------------------------------------------------------------------------------------------------------------------------------------- */
/*------------------------------------------------------------------------------------------------------------------------------------------------- */
    /**
     * Teste la méthode restaurantExists lorsque le restaurant n'existe pas.
     * On simule que le repository retourne un Optional vide.
     */
    @Test
    public void testRestaurantExists_ReturnsFalse() {
        // Création d'un restaurant dont l'existence est à vérifier
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Test Restaurant");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue Test");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        
        // Simulation : le repository retourne un Optional vide (le restaurant n'existe pas)
        when(restaurantRepository.findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
             restaurant.getNomResto(),
             restaurant.getCodePostal(),
             restaurant.getVille(),
             restaurant.getNomRue(),
             restaurant.getNumRue()
        )).thenReturn(Optional.empty());
        
        // Appel de la méthode à tester
        boolean exists = restaurantService.restaurantExists(restaurant);
        
        // On s'attend à ce que la méthode retourne false
        assertFalse(exists, "La méthode doit retourner false car le restaurant n'existe pas");
        
        // Vérifier que le repository a été appelé une fois
        verify(restaurantRepository, times(1)).findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
             restaurant.getNomResto(),
             restaurant.getCodePostal(),
             restaurant.getVille(),
             restaurant.getNomRue(),
             restaurant.getNumRue()
        );
    }
    /*------------------------------------------------------------------------------------------------------------------------------------------------- */
}
