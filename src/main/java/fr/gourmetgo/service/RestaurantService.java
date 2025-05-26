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
//--------------------------------------------------------------------------------------------
                 /* ------------------ Partie Adam  ------------------ */
//--------------------------------------------------------------------------------------------
/*---------------------------------------------------------------------------------------------------------------------------------- */
    /**
 * Enregistre le restaurant passé en paramètre dans la base de données via le repository.
 * <p>
 * Cette méthode appelle la méthode {@code save} du {@code restaurantRepo} pour enregistrer
 * le restaurant et retourner l'objet mis à jour, incluant notamment l'ID généré par la base de données.
 * </p>
 *
 * @param restaurant l'objet Restaurant à enregistrer
 * @return le Restaurant enregistré avec son ID attribué
 */
public Restaurant enregistrerRestaurant(Restaurant restaurant) {
    return restaurantRepo.save(restaurant);// Renvoie l'objet Restaurant enregistré, incluant l'ID généré
}
/*---------------------------------------------------------------------------------------------------------------------------------- */
/*---------------------------------------------------------------------------------------------------------------------------------- */

/**
 * Vérifie si un restaurant correspondant aux caractéristiques fournies existe déjà en base.
 * <p>
 * La recherche est effectuée en se basant sur le nom du restaurant, le code postal, la ville,
 * le nom de la rue et le numéro de rue. Si un restaurant présentant ces mêmes attributs est trouvé,
 * la méthode retourne {@code true}.
 * </p>
 *
 * @param restaurant l'objet Restaurant dont il faut vérifier l'existence
 * @return {@code true} si un restaurant équivalent est présent en base, {@code false} sinon
 */
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
/*---------------------------------------------------------------------------------------------------------------------------------- */
/*---------------------------------------------------------------------------------------------------------------------------------- */
//--------------------------------------------------------------------------------------------
        /* ------------------ FIN Partie Adam ------------------ */
//--------------------------------------------------------------------------------------------


    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }


    

    
}


