package fr.gourmetgo.service;

import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
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
 

    @Test
    public void testGetRestaurantById_found(){
        long id = 1L;
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(id);
        mockRestaurant.setNomResto("Chez dede");

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(mockRestaurant));

        Restaurant result = restaurantService.getRestaurantById(id);

        assertNotNull(result); //vérifie que le restaurant est bien retourné
        assertEquals(id, result.getId()); //vérifie que l'id correspond
        assertEquals("Chez dede", result.getNomResto());
    }

    @Test
    public void testGetRestaurantById_notFound(){
        long id = 999L;

        when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());

        Restaurant result = restaurantService.getRestaurantById(id);

        assertNull(result);
    }
    
    @Test
    public void testGetAllRestaurant(){
        Restaurant r1 = new Restaurant();
        r1.setId(1L);
        r1.setNomResto("Chez Dede");

        Restaurant r2 = new Restaurant();
        r2.setId(2L);;
        r2.setNomResto("Chez Patou");

        List<Restaurant> mockList = Arrays.asList(r1,r2);

        when(restaurantRepository.findAll()).thenReturn(mockList);
        
        List<Restaurant> result = restaurantService.getAllRestaurants();

        assertNotNull(result); // la liste ne doit pas être nulle
        assertEquals(2, result.size()); // elle doit contenir deux éléments
        assertEquals("Chez Dede", result.get(0).getNomResto()); // le premier restaurant doit être "chez Dede"
        assertEquals("Chez Patou", result.get(1).getNomResto()); // le second "Chez Patou"
    }

    @Test
    public void testSupprimerRestaurant_exist(){
        long id = 1L;

        when(restaurantRepository.existsById(id)).thenReturn(true);

        restaurantService.supprimerRestaurant(id);

        //vérifie que deleteById est appelé avec le bon id
        verify(restaurantRepository, times(1)).deleteById(id);
    }

    @Test
    public void testSupprimerRestaurant_notExist() {
    long id = 999L;

    when(restaurantRepository.existsById(id)).thenReturn(false);

    //on vérifie qu’une EntityNotFoundException est bien levée
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        restaurantService.supprimerRestaurant(id);
    });

    //vérifier le message de l'exception si besoin
    assertEquals("Restaurant introuvable", exception.getMessage());

    //on s’assure que deleteById n’a PAS été appelé
    verify(restaurantRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testUpdateRestaurant_tousChampsInclusImage() {
    Long id = 1L;
    Gerant ancienGerant = new Gerant();
    ancienGerant.setNom("Ancien Gerant");
    Gerant nouveauGerant = new Gerant();
    nouveauGerant.setNom("Jean Dupont");

    // Restaurant déjà en base
    Restaurant restaurantEnBase = new Restaurant();
    restaurantEnBase.setId(id);
    restaurantEnBase.setNomResto("Ancien Nom");
    restaurantEnBase.setVille("Ancienne Ville");
    restaurantEnBase.setCodePostal("00000");
    restaurantEnBase.setTelResto("0000000000");
    restaurantEnBase.setTypeResto("Ancien Type");
    restaurantEnBase.setHeureOuverture(LocalTime.of(8, 0));
    restaurantEnBase.setHeureFermeture(LocalTime.of(18, 0));
    restaurantEnBase.setAutreType("Ancien Autre");
    restaurantEnBase.setNomRue("Ancienne Rue");
    restaurantEnBase.setNumRue("1");
    restaurantEnBase.setGerant(ancienGerant);;
    restaurantEnBase.setImageResto("ancienne-image.jpg");

    // Données à mettre à jour
    Restaurant restaurantModifie = new Restaurant();
    restaurantModifie.setId(id);
    restaurantModifie.setNomResto("Nouveau Nom");
    restaurantModifie.setVille("Nouvelle Ville");
    restaurantModifie.setCodePostal("75001");
    restaurantModifie.setTelResto("0123456789");
    restaurantModifie.setTypeResto("Italien");
    restaurantModifie.setHeureOuverture(LocalTime.of(11, 30)); // 11:30
    restaurantModifie.setHeureFermeture(LocalTime.of(23, 0));  // 23:00
    restaurantModifie.setAutreType("Végétarien");
    restaurantModifie.setNomRue("Rue de Paris");
    restaurantModifie.setNumRue("42");
    restaurantModifie.setGerant(nouveauGerant);
    restaurantModifie.setImageResto("nouvelle-image.jpg");

    // Simulation de l'existence du restaurant en base
    when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurantEnBase));

    restaurantService.updateRestaurant(restaurantModifie);

    // tous les champs doivent avoir été mis à jour
    assertEquals("Nouveau Nom", restaurantEnBase.getNomResto());
    assertEquals("Nouvelle Ville", restaurantEnBase.getVille());
    assertEquals("75001", restaurantEnBase.getCodePostal());
    assertEquals("0123456789", restaurantEnBase.getTelResto());
    assertEquals("Italien", restaurantEnBase.getTypeResto());
    assertEquals(LocalTime.of(11, 30), restaurantEnBase.getHeureOuverture());
    assertEquals(LocalTime.of(23, 0), restaurantEnBase.getHeureFermeture());
    assertEquals("Végétarien", restaurantEnBase.getAutreType());
    assertEquals("Rue de Paris", restaurantEnBase.getNomRue());
    assertEquals("42", restaurantEnBase.getNumRue());
    assertEquals("Jean Dupont", restaurantEnBase.getGerant().getNom());
    assertEquals("nouvelle-image.jpg", restaurantEnBase.getImageResto());

    // Et on vérifie que la sauvegarde a bien été faite
    verify(restaurantRepository).save(restaurantEnBase);
    }

}