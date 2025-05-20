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
    model.addAttribute("restaurant", new Restaurant()); // Ajout du modèle Thymeleaf
    return "formulaire"; // Retourne la vue Thymeleaf
}


    @PostMapping("/formulaire")
    public String inscrireRestaurant(@Valid @ModelAttribute("restaurant") Restaurant restaurant, BindingResult result, Model model) {
    if (result.hasErrors()) {
        model.addAttribute("restaurant", restaurant); // Recharge l’objet avec erreurs
        return "formulaire"; // Retourne le formulaire avec erreurs visibles
    }

    // Enregistre le restaurant et récupère son ID
    Restaurant nouveauRestaurant = restaurantService.enregistrerRestaurant(restaurant);

    // Stocke l’ID du restaurant pour l'utiliser dans `success.html`
    model.addAttribute("restaurantId", nouveauRestaurant.getIdResto());

     return "redirect:/restaurants/success";

 // Affiche la page "Inscription réussie !" avant les détails
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



