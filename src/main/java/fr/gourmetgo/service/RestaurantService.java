package fr.gourmetgo.service;

import fr.gourmetgo.entite.Restaurant;
import fr.gourmetgo.repository.AppRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RestaurantService {

    @Autowired
    private AppRepository appRepository;
    
    public void enregistrerRestaurant(Restaurant restaurant) {
        appRepository.save(restaurant); // Enregistre le restaurant dans la base de données
    }

    public List<Restaurant> getAllRestaurants() {
    return appRepository.findAll();
}
}

