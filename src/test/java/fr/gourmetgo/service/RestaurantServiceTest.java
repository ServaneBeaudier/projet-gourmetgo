package fr.gourmetgo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;

public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepo;

    @InjectMocks
    private RestaurantService restaurantService;

    //initialise les mocks avant chaque test
    public RestaurantServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRestaurantById_found(){
        long id = 1L;
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(id);
        mockRestaurant.setNomResto("Chez dede");

        when(restaurantRepo.findById(id)).thenReturn(Optional.of(mockRestaurant));

        Restaurant result = restaurantService.getRestaurantById(id);

        assertNotNull(result); //vérifie que le restaurant est bien retourné
        assertEquals(id, result.getId()); //vérifie que l'id correspond
        assertEquals("Chez dede", result.getNomResto());
    }

    @Test
    public void testGetRestaurantById_notFound(){
        long id = 999L;

        when(restaurantRepo.findById(null)).thenReturn(Optional.empty());

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

        when(restaurantRepo.findAll()).thenReturn(mockList);
        
        List<Restaurant> result = restaurantService.getAllRestaurants();

        assertNotNull(result); // la liste ne doit pas être nulle
        assertEquals(2, result.size()); // elle doit contenir deux éléments
        assertEquals("Chez Dede", result.get(0).getNomResto()); // le premier restaurant doit être "chez Dede"
        assertEquals("Chez Patou", result.get(1).getNomResto()); // le second "Chez Patou"
    }

    @Test
    public void testSupprimerRestaurant_exist(){
        long id = 1L;

        when(restaurantRepo.existsById(id)).thenReturn(true);

        restaurantService.supprimerRestaurant(id);

        //vérifie que deleteById est appelé avec le bon id
        verify(restaurantRepo, times(1)).deleteById(id);
    }

    @Test
    public void testSupprimerRestaurant_notExist() {
    long id = 999L;

    when(restaurantRepo.existsById(id)).thenReturn(false);

    //on vérifie qu’une EntityNotFoundException est bien levée
    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        restaurantService.supprimerRestaurant(id);
    });

    //vérifier le message de l'exception si besoin
    assertEquals("Restaurant introuvable", exception.getMessage());

    //on s’assure que deleteById n’a PAS été appelé
    verify(restaurantRepo, never()).deleteById(anyLong());
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
    when(restaurantRepo.findById(id)).thenReturn(Optional.of(restaurantEnBase));

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
    verify(restaurantRepo).save(restaurantEnBase);
}

}
