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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    // 1. Gestion de l'image d'abord
    if (!imageFile.isEmpty()) {
        try {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            restaurant.setImageResto("/uploads/" + fileName); // Affecte ici AVANT la validation
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("uploadError", "Erreur lors de l'envoi de l'image.");
            return "formulaire";
        }
    } else {
        result.rejectValue("imageResto", "imageFile.empty", "Veuillez ajouter une image.");
    }

    // 2. Ensuite seulement on valide
    if (result.hasErrors()) {
        return "formulaire";
    }

    // 3. Vérifie si le restaurant existe déjà
    if (restaurantService.restaurantExists(restaurant)) {
        result.reject("duplicate", "Ce restaurant existe déjà.");
        return "formulaire";
    }

    // 4. Sauvegarde
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
        if (restaurant == null) {
        return "redirect:/admin/list";
    }
        model.addAttribute("restaurant", restaurant);
        return "fiche_restaurant";
    }



    @GetMapping("/restaurants/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "edit_restaurant";
    }



    @PostMapping("/restaurants/update")
    public String updateRestaurant(
        @ModelAttribute("restaurant") Restaurant restaurant,
        @RequestParam("imageFile") MultipartFile imageFile) {

    // Charger le restaurant existant
    Restaurant restaurantEnBase = restaurantService.getRestaurantById(restaurant.getId());

    if (!imageFile.isEmpty()) {
        try {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Mettre à jour le chemin de l'image dans l'objet temporaire
            restaurant.setImageResto("/uploads/" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            // Tu peux ajouter un message d’erreur ici si tu veux
        }
    } else {
        // Si pas de nouvelle image, garde l’ancienne dans l’objet à envoyer au service
        restaurant.setImageResto(restaurantEnBase.getImageResto());
    }

    restaurantService.updateRestaurant(restaurant);

    return "redirect:/admin/list/" + restaurant.getId();
    }


    @PostMapping("/restaurants/supprimer/{id}")
    public String supprimerRestaurant(@PathVariable Long id, RedirectAttributes redirectAttributes){
        restaurantService.supprimerRestaurant(id);
        redirectAttributes.addFlashAttribute("message", "Le restaurant a bien été supprimé");
        return "redirect:/admin/list";
    }






    



}




