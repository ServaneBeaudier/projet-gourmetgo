package fr.gourmetgo.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String updateRestaurant(@ModelAttribute("restaurant") Restaurant restaurant) {
        restaurantService.updateRestaurant(restaurant);
        return "redirect:/restaurants";
    }

    @GetMapping("/list")
    public String listRestaurants(Model model) {
    List<Restaurant> restaurants = restaurantService.getAllRestaurants();
    model.addAttribute("restaurants", restaurants);
    return "liste_restaurant";
}
}
