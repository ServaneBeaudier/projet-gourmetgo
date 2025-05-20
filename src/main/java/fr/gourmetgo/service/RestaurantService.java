package fr.gourmetgo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.repository.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    public Restaurant getRestaurantById(long id){
        return restaurantRepo.findById(id).orElse(null);
    }

    public Restaurant updateRestaurant(Restaurant restaurant){
        return restaurantRepo.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }



}
