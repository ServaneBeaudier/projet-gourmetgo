package fr.gourmetgo.control;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.service.RestaurantService;

public class RestaurantControllerTest {

    // Crée un mock de RestaurantService pour simuler son comportement
    @Mock
    private RestaurantService restaurantService;

    @InjectMocks //créé une instance de restaurantcontroller et injecte le mock dedans
    private RestaurantController restaurantController;

    // Déclare une instance de MockMvc pour simuler les requêtes HTTP
    private MockMvc mockMvc;

    // Méthode exécutée avant chaque test pour initialiser les mocks et configurer MockMvc
    @BeforeEach
    public void setUp() {
        // Initialise les annotations Mockito pour cette classe de test
        MockitoAnnotations.openMocks(this);
        // Configure MockMvc pour tester le contrôleur RestaurantController
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    // Méthode de test pour la méthode updateRestaurant du contrôleur
    @Test
    public void testUpdateRestaurant() throws Exception {
        // Crée un objet Restaurant avec des valeurs initiales
        Restaurant restaurant = new Restaurant("adresse", "Chez Dede", "de 10h à 12h", "0250505050", "Bistrot", null);
        // Définit l'ID du restaurant
        restaurant.setId(1L);

         // Configure le mock restaurantService pour retourner l'objet Restaurant lorsque updateRestaurant est appelé
        when(restaurantService.updateRestaurant(any(Restaurant.class))).thenReturn(restaurant);

        // Utilise MockMvc pour envoyer une requête POST à l'endpoint /restaurants/update
        // avec l'objet Restaurant comme attribut de modèle
        mockMvc.perform(post("/restaurants/update")
                .flashAttr("restaurant", restaurant))
                // Vérifie que la réponse est une redirection (code 3xx)
                .andExpect(status().is3xxRedirection())
                // Vérifie que la redirection est vers l'URL /restaurants
                .andExpect(redirectedUrl("/restaurants"));

        // Vérifie que la méthode updateRestaurant du service a été appelée une fois avec l'objet Restaurant
        verify(restaurantService, times(1)).updateRestaurant(restaurant);
    }
}
