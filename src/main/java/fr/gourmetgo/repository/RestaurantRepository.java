package fr.gourmetgo.repository; 

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.gourmetgo.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
        String nomResto, 
        String codePostal,
        String ville,
        String nomRue,
        String numRue);
}





