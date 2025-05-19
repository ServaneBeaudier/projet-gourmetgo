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

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/formulaire")
    public String afficherFormulaire(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "formulaire"; // Affiche la page Thymeleaf du formulaire
    }

    @PostMapping("/formulaire")
    public String inscrireRestaurant(@Valid @ModelAttribute Restaurant restaurant, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "formulaire"; // Retourne la page avec les erreurs affichées
        }

        restaurantService.enregistrerRestaurant(restaurant);
        return "redirect:/success"; // Redirection vers une page succès
    }
}
