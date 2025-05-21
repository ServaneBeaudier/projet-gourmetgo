package fr.gourmetgo.service;

import fr.gourmetgo.entite.Restaurant;
import fr.gourmetgo.repository.AppRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RestaurantService {

    @Autowired
    private AppRepository appRepository;
    
    public Restaurant enregistrerRestaurant(Restaurant restaurant) {
        return appRepository.save(restaurant); // Retourne l’objet enregistré avec son ID
    }

    public List<Restaurant> getAllRestaurants() {
    return appRepository.findAll();
}

public Restaurant trouverRestaurantParId(Long idResto) {
    return appRepository.findById(idResto).orElse(null); //  Retourne le restaurant ou `null` s'il n'existe pas
}

 /*public boolean restaurantExists(Restaurant restaurant) {
        Optional<Restaurant> existing = appRepository.findByNomRestoAndAdresse(
            restaurant.getNomResto(), restaurant.getAdresse());
        return existing.isPresent();*/
        public boolean restaurantExists(Restaurant restaurant) {
    Optional<Restaurant> existing = appRepository.findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
        restaurant.getNomResto(),
        restaurant.getCodePostal(),
        restaurant.getVille(),
        restaurant.getNomRue(),
        restaurant.getNumRue()
    );
    return existing.isPresent();
}
    }




