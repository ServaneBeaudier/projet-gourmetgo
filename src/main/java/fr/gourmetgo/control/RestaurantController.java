package fr.gourmetgo.control;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.service.RestaurantService;

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

    @GetMapping("/restaurants/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "edit_restaurant";
    }

    @GetMapping("/list/{id}")
    public String showFicheRestaurant(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "fiche_restaurant";
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
            restaurant.setImagePath("/uploads/" + fileName); // Chemin relatif pour le front

        } catch (IOException e) {
            e.printStackTrace();
            // Tu peux ici rediriger vers une page d'erreur ou afficher un message
        }
    }

    // Appel du service pour mettre à jour
    restaurantService.updateRestaurant(restaurant);

    return "redirect:/admin/list/" + restaurant.getId();
}

    @GetMapping("/list")
    public String listRestaurants(Model model) {
    List<Restaurant> restaurants = restaurantService.getAllRestaurants();
    model.addAttribute("restaurants", restaurants);
    return "liste_restaurant";
}
}
