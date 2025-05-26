package fr.gourmetgo.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.gourmetgo.controller.RestaurantController;
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

        // Simule un fichier image envoyé
        MultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "dummy image content".getBytes()
        );

        // Simule un restaurant déjà présent en base
        Restaurant enBase = new Restaurant();
        enBase.setId(id);
        enBase.setImageResto("/uploads/ancienne.jpg");

        when(restaurantService.getRestaurantById(id)).thenReturn(enBase);

        // ACT
        String result = restaurantController.updateRestaurant(restaurant, imageFile);

        // ASSERT
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
}
