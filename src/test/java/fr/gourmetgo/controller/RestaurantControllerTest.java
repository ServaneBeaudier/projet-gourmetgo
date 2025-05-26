package fr.gourmetgo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.service.RestaurantService;

@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setupReflection() {
        // Utilisé uniquement si nécessaire pour tests hors Mockito (ex: sans @InjectMocks)
        // ReflectionTestUtils.setField(restaurantController, "restaurantService", restaurantService);
    }

    @Test
    void testListRestaurants() {
        Restaurant resto1 = new Restaurant();
        resto1.setId(1L);
        resto1.setNomResto("Chez Dede");

        Restaurant resto2 = new Restaurant();
        resto2.setId(2L);
        resto2.setNomResto("Chez Patou");

        List<Restaurant> restaurants = Arrays.asList(resto1, resto2);
        when(restaurantService.getAllRestaurants()).thenReturn(restaurants);

        String viewName = restaurantController.listRestaurants(model);

        assertEquals("liste_restaurant", viewName);
        verify(restaurantService).getAllRestaurants();
        verify(model).addAttribute("restaurants", restaurants);
    }

    @Test
    void testShowFicheRestaurant_RestaurantFound() {
        Long id = 1L;
        Restaurant resto = new Restaurant();
        resto.setId(id);
        resto.setNomResto("Chez Dede");

        when(restaurantService.getRestaurantById(id)).thenReturn(resto);

        String viewName = restaurantController.showFicheRestaurant(id, model);

        assertEquals("fiche_restaurant", viewName);
        verify(restaurantService).getRestaurantById(id);
        verify(model).addAttribute("restaurant", resto);
    }

    @Test
    void testShowFicheRestaurant_RestaurantNotFound() {
        Long id = 999L;
        when(restaurantService.getRestaurantById(id)).thenReturn(null);

        String viewName = restaurantController.showFicheRestaurant(id, model);

        assertEquals("redirect:/admin/list", viewName);
        verify(restaurantService).getRestaurantById(id);
        verifyNoInteractions(model);
    }

    @Test
    void testShowEditForm() {
        Long id = 1L;
        Restaurant resto = new Restaurant();
        resto.setId(id);
        resto.setNomResto("Chez Dede");

        when(restaurantService.getRestaurantById(id)).thenReturn(resto);

        String viewName = restaurantController.showEditForm(id, model);

        assertEquals("edit_restaurant", viewName);
        verify(restaurantService).getRestaurantById(id);
        verify(model).addAttribute("restaurant", resto);
    }

    @Test
    void testUpdateRestaurant_withNewImage() throws IOException {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setNomResto("Chez Dede");

        MultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes()
        );

        Restaurant enBase = new Restaurant();
        enBase.setId(id);
        enBase.setImageResto("/uploads/ancienne.jpg");

        when(restaurantService.getRestaurantById(id)).thenReturn(enBase);

        String result = restaurantController.updateRestaurant(restaurant, imageFile);

        verify(restaurantService).updateRestaurant(any(Restaurant.class));
        assertTrue(result.contains("redirect:/admin/list/"));
    }

    @Test
    void testSupprimerRestaurant() {
        Long id = 5L;

        String result = restaurantController.supprimerRestaurant(id, redirectAttributes);

        verify(restaurantService, times(1)).supprimerRestaurant(id);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Le restaurant a bien été supprimé");
        assertEquals("redirect:/admin/list", result);
    }

    @Test
    public void testAfficherFormulaire() {
        Model model = new ExtendedModelMap();
        String vue = restaurantController.afficherFormulaire(model);
        assertEquals("formulaire", vue);
        assertNotNull(model.getAttribute("restaurant"));
    }

    @Test
    public void testInscrireRestaurant_Success() throws IOException {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        Model model = new ExtendedModelMap();

        when(restaurantService.restaurantExists(any(Restaurant.class))).thenReturn(false);

        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setId(1L);
        savedRestaurant.setNomResto("Restaurant A");
        when(restaurantService.enregistrerRestaurant(any(Restaurant.class))).thenReturn(savedRestaurant);

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        assertTrue(vueRetour.startsWith("redirect:/admin/success"));
        assertFalse(result.hasErrors());
    }

    @Test
    public void testInscrireRestaurant_ImageVide() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));

        MockMultipartFile imageFile = new MockMultipartFile("imageFile", new byte[0]);

        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        Model model = new ExtendedModelMap();

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        assertEquals("formulaire", vueRetour);
        assertTrue(result.hasFieldErrors("imageResto"));
    }

    @Test
    public void testInscrireRestaurant_RestaurantExists() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("Restaurant A");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        Model model = new ExtendedModelMap();

        when(restaurantService.restaurantExists(any(Restaurant.class))).thenReturn(true);

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        assertEquals("formulaire", vueRetour);
        assertTrue(result.hasGlobalErrors());
        assertEquals("Ce restaurant existe déjà.", result.getGlobalError().getDefaultMessage());
    }

    @Test
    public void testInscrireRestaurant_BindingErrors() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("");
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        result.rejectValue("nomResto", "error", "Le nom du restaurant est obligatoire");

        Model model = new ExtendedModelMap();

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        assertEquals("formulaire", vueRetour);
        assertTrue(result.hasFieldErrors("nomResto"));
    }
}
