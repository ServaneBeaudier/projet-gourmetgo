package fr.gourmetgo.controller;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantControllerTest {

    private RestaurantService restaurantService;
    private RestaurantController restaurantController;

    @BeforeEach
    public void setUp() {
        // On créer un mock du service
        restaurantService = mock(RestaurantService.class);
        // On instancie le contrôleur
        restaurantController = new RestaurantController();
        // Injection dans le champ privé "restaurantService"
    ReflectionTestUtils.setField(restaurantController, "restaurantService", restaurantService);
}

    /**
     * Vérifie que la méthode GET pour afficher le formulaire
     * ajoute bien un Restaurant vide dans le modèle et retourne "formulaire".
     */
    @Test
    public void testAfficherFormulaire() {
        Model model = new ExtendedModelMap();// Création d'un modèle vide pour le test
        String vue = restaurantController.afficherFormulaire(model);// Appel de la méthode à tester
        assertEquals("formulaire", vue, "La vue renvoyée doit être 'formulaire'");// Vérification de la vue retournée
        assertNotNull(model.getAttribute("restaurant"), "Le modèle doit contenir une instance de Restaurant");// Vérification que le modèle contient un attribut "restaurant"
    }

    /**
     * Teste le cas d'inscription réussi.
     * On simule que :
     *  - un fichier image est envoyé et traité correctement,
     *  - il n'y a pas d'erreurs de validation,
     *  - le restaurant n'existe pas déjà.
     * La méthode doit alors sauvegarder le restaurant et rediriger vers la page succès.
     */
    @Test
    public void testInscrireRestaurant_Success() throws IOException {
        // Création d'une instance Restaurant avec toutes les infos nécessaires.
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

        // Création d'un fichier image (MockMultipartFile)
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        // Construction du BindingResult (aucune erreur initiale)
        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        Model model = new ExtendedModelMap();

        // On simule que le restaurant n'existe pas déjà.
        when(restaurantService.restaurantExists(any(Restaurant.class))).thenReturn(false);

        // On simule l'enregistrement du restaurant et le renvoi d'une instance avec ID définitif.
        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setId(1L);
        savedRestaurant.setNomResto("Restaurant A");
        when(restaurantService.enregistrerRestaurant(any(Restaurant.class))).thenReturn(savedRestaurant);           

        // Appel de la méthode à tester
        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model); 

        // Vérification : on s'attend à une redirection vers la page succès.
        assertTrue(vueRetour.startsWith("redirect:/admin/success"),
                "La redirection vers la page succès est attendue lorsque l'inscription se déroule correctement.");
        // On s'assure que le BindingResult ne comporte pas d'erreur.
        assertFalse(result.hasErrors(), "Le BindingResult ne doit contenir aucune erreur.");
    }

    /**
     * Teste le cas où le fichier image n'est pas fourni.
     * Dans ce cas, on attend que la méthode rejette le champ "imageResto"
     * et renvoie la vue "formulaire".
     */
    @Test
    public void testInscrireRestaurant_ImageVide() {
        // Création d'une instance Restaurant avec des données valides.
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

        // Création d'un fichier image vide.
        MockMultipartFile imageFile = new MockMultipartFile("imageFile", new byte[0]);

        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        Model model = new ExtendedModelMap();

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        // On s'attend à ce que le formulaire soit renvoyé.
        assertEquals("formulaire", vueRetour, "La vue retournée doit être 'formulaire' en cas d'image vide.");
        // On s'attend à une erreur sur le champ imageResto dans le BindingResult.
        assertTrue(result.hasFieldErrors("imageResto"),
                "Le BindingResult doit contenir une erreur pour le champ 'imageResto'.");
    }

    /**
     * Teste le cas où le restaurant existe déjà.
     * La méthode doit ajouter une erreur globale et renvoyer "formulaire".
     */
    @Test
    public void testInscrireRestaurant_RestaurantExists() {
        // Préparation d'un Restaurant avec des données valides.
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

        // On fournit un fichier image valide
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        Model model = new ExtendedModelMap();

        // Simulation : le restaurant existe déjà.
        when(restaurantService.restaurantExists(any(Restaurant.class))).thenReturn(true);

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        // La vue doit être le formulaire car l'inscription est refusée.
        assertEquals("formulaire", vueRetour, "La vue doit être 'formulaire' si le restaurant existe déjà.");
        // Vérification de l'erreur globale
        assertTrue(result.hasGlobalErrors(), "Le BindingResult doit contenir une erreur globale.");
        assertEquals("Ce restaurant existe déjà.", result.getGlobalError().getDefaultMessage(),
                "Le message d'erreur global doit indiquer que le restaurant existe déjà.");
    }

    /**
     * Teste le cas où des erreurs de binding (validation) sont présentes.
     * Pour reproduire ce cas, on ajoute manuellement une erreur sur un champ (ici, nomResto).
     */
    @Test
    public void testInscrireRestaurant_BindingErrors() {
        // Création d'un Restaurant invalide (par exemple, nom vide)
        Restaurant restaurant = new Restaurant();
        restaurant.setNomResto("");  // Ce champ est invalide (NotBlank attendu)
        restaurant.setNumRue("123");
        restaurant.setNomRue("Rue de Paris");
        restaurant.setCodePostal("75001");
        restaurant.setVille("Paris");
        restaurant.setTelResto("0123456789");
        restaurant.setTypeResto("Sushi");
        restaurant.setHeureOuverture(LocalTime.of(9, 0));
        restaurant.setHeureFermeture(LocalTime.of(22, 0));

        // Fourniture d'une image correcte pour ne pas avoir d'erreur sur ce point.
        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "fake image content".getBytes()
        );

        // On simule des erreurs de validation en ajoutant une erreur pour "nomResto"
        BindingResult result = new BeanPropertyBindingResult(restaurant, "restaurant");
        result.rejectValue("nomResto", "error", "Le nom du restaurant est obligatoire");

        Model model = new ExtendedModelMap();

        String vueRetour = restaurantController.inscrireRestaurant(restaurant, result, imageFile, model);

        // Comme il y a une erreur, la vue retournée doit être le formulaire.
        assertEquals("formulaire", vueRetour, "En cas d'erreurs de validation, le formulaire doit être réaffiché.");
        assertTrue(result.hasFieldErrors("nomResto"), "Le BindingResult doit indiquer une erreur pour 'nomResto'.");
    }
}
