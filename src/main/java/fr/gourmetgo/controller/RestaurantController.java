package fr.gourmetgo.controller;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.service.RestaurantService;
import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public String accueilGR() {
        return "accueilGR"; 
    }

    @GetMapping("/dashboard")
    public String connexionOK() {
        return "dashboard"; 
    }

    @GetMapping("/restaurants/formulaire")
    public String afficherFormulaire(Model model) {
    Restaurant restaurant = new Restaurant(); // Déclaration de la variable 'r'
    restaurant.setTypeResto("");    // On s'assure que le champ type de restaurant est vide
    model.addAttribute("restaurant", restaurant);
    return "formulaire";
    }


   @PostMapping("/formulaire")
public String inscrireRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant,
                                 BindingResult result,
                                 @RequestParam("imageFile") MultipartFile imageFile,
                                 Model model) {
    if (result.hasErrors()) {
        return "formulaire";
    }

    if (restaurantService.restaurantExists(restaurant)) {
        result.reject("duplicate", "Ce restaurant existe déjà.");
        return "formulaire";
    }

    if (!imageFile.isEmpty()) {
        try {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            restaurant.setImageResto("/uploads/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("uploadError", "Erreur lors de l'envoi de l'image.");
            return "formulaire";
        }
    }

    Restaurant nouveauRestaurant = restaurantService.enregistrerRestaurant(restaurant);
    return "redirect:/admin/success?restaurantId=" + nouveauRestaurant.getId();
}


    @GetMapping("/success")
    public String afficherSuccess() {
        return "success"; // Affiche la vue success.html

    }

    @GetMapping("/list")
    public String listRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "liste_restaurant";
    }

    @GetMapping("/list/{id}")
    public String showFicheRestaurant(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "fiche_restaurant";
    }

    @GetMapping("/restaurants/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "edit_restaurant";
    }

    @PostMapping("/restaurants/update")
    public String updateRestaurant(@ModelAttribute("restaurant") Restaurant restaurant,
                               @RequestParam("image") MultipartFile imageFile) {
    // Vérifie si une image a été envoyée
    if (!imageFile.isEmpty()) {
        try {
            // Crée un nom unique pour l'image
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            // Définit le dossier d'upload
            Path uploadDir = Paths.get("uploads"); // à la racine du projet
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Enregistre le fichier sur le disque
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Met à jour le chemin de l’image dans l’objet
            restaurant.setImageResto("/uploads/" + fileName); // Chemin relatif pour le front

        } catch (IOException e) {
            e.printStackTrace();
            // Tu peux ici rediriger vers une page d'erreur ou afficher un message
        }
    }

    // Appel du service pour mettre à jour
    restaurantService.updateRestaurant(restaurant);

    return "redirect:/admin/list/" + restaurant.getId();
    }

    



}




