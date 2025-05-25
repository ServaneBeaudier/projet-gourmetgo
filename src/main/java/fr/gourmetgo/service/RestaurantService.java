package fr.gourmetgo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    public Restaurant getRestaurantById(long id){
        return restaurantRepo.findById(id).orElse(null);
    }

    public void updateRestaurant(Restaurant restaurant){
        Restaurant restaurantEnBase = restaurantRepo.findById(restaurant.getId())
                .orElseThrow(() -> new EntityNotFoundException("Restaurant non trouvé"));

        // Mise à jour manuelle des champs modifiables
        restaurantEnBase.setNomResto(restaurant.getNomResto());
        restaurantEnBase.setVille(restaurant.getVille());
        restaurantEnBase.setCodePostal(restaurant.getCodePostal());
        restaurantEnBase.setTelResto(restaurant.getTelResto());
        restaurantEnBase.setTypeResto(restaurant.getTypeResto());
        restaurantEnBase.setHeureOuverture(restaurant.getHeureOuverture());
        restaurantEnBase.setHeureFermeture(restaurant.getHeureFermeture());
        restaurantEnBase.setAutreType(restaurant.getAutreType());
        restaurantEnBase.setNomRue(restaurant.getNomRue());
        restaurantEnBase.setNumRue(restaurant.getNumRue());
        restaurantEnBase.setGerant(restaurant.getGerant());

        // Mise à jour conditionnelle de l'image (ne pas écraser si null)
        if (restaurant.getImageResto() != null) {
            restaurantEnBase.setImageResto(restaurant.getImageResto());
        }

        restaurantRepo.save(restaurantEnBase);
    }

    public Restaurant enregistrerRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant); // Retourne l’objet enregistré avec son ID
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    public boolean restaurantExists(Restaurant restaurant) {
    Optional<Restaurant> existing = restaurantRepo.findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
        restaurant.getNomResto(),
        restaurant.getCodePostal(),
        restaurant.getVille(),
        restaurant.getNomRue(),
        restaurant.getNumRue()
    );
    return existing.isPresent();    
    }

    
}


