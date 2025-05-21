package fr.gourmetgo.controller;

import fr.gourmetgo.service.RestaurantService;
import fr.gourmetgo.entite.Restaurant;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/formulaire")
public String afficherFormulaire(Model model) {
    model.addAttribute("restaurant", new Restaurant()); // ✅ Crée un objet vide à chaque chargement
    return "formulaire"; // Retourne la vue Thymeleaf
}



   @PostMapping("/formulaire")
public String inscrireRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant, BindingResult result, Model model) {
    if (result.hasErrors()) {
        // Si il y a déjà des erreurs de validation, on retourne le formulaire
        return "formulaire";
    }
    
    // Vérifier si le restaurant existe déjà selon vos critères
    if (restaurantService.restaurantExists(restaurant)) {
        // Ajouter une erreur globale (ou sur un champ en particulier) dans BindingResult
        result.reject("duplicate", "Ce restaurant existe déjà.");
        return "formulaire";
    }
    
    // Enregistre le restaurant s'il n'existe pas déjà
    Restaurant nouveauRestaurant = restaurantService.enregistrerRestaurant(restaurant);
    
    return "redirect:/restaurants/success?restaurantId=" + nouveauRestaurant.getIdResto();
}



@GetMapping("/success")
public String afficherSuccess() {
    return "success"; // Affiche la vue success.html

}

@GetMapping("/{idResto}")
public String afficherDetailsRestaurant(@PathVariable Long idResto, Model model) {
    Restaurant restaurant = restaurantService.trouverRestaurantParId(idResto); // Cherche le restaurant par ID
    model.addAttribute("restaurant", restaurant);
    return "details"; // Retourne la vue `details.html`
} 






}




