package fr.gourmetgo.repository; 

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.gourmetgo.entity.Restaurant;

/**
* Interface de repository pour l'entité Restaurant.
* Cette interface étend JpaRepository pour fournir des méthodes CRUD de base
* ainsi que des méthodes de requête personnalisées pour l'entité Restaurant.
*/
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    /**
    * Recherche un restaurant par son nom, code postal, ville, nom de rue et numéro de rue.
    * @param nomResto Le nom du restaurant.
    * @param codePostal Le code postal du restaurant.
    * @param ville La ville du restaurant.
    * @param nomRue Le nom de la rue du restaurant.
    * @param numRue Le numéro de rue du restaurant.
    * @return Un Optional contenant le restaurant trouvé, ou un Optional vide si aucun restaurant ne correspond aux critères.
    */
    Optional<Restaurant> findByNomRestoAndCodePostalAndVilleAndNomRueAndNumRue(
        String nomResto, 
        String codePostal,
        String ville,
        String nomRue,
        String numRue);

        
}









