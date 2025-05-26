package fr.gourmetgo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gourmetgo.entity.Restaurant;
import fr.gourmetgo.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;

/**
* Service pour la gestion des restaurants.
*
* Cette classe fournit des méthodes pour la gestion des restaurants, telles que la récupération,
* la mise à jour, l'enregistrement, la suppression et la vérification de l'existence des restaurants.
*/
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    /**
    * Récupère un restaurant par son identifiant.
    * @param id L'identifiant du restaurant.
    * @return Le restaurant trouvé, ou null si aucun restaurant n'est trouvé.
    */
    public Restaurant getRestaurantById(long id){
        return restaurantRepo.findById(id).orElse(null);
    }

    /**
    * Met à jour les informations d'un restaurant.
    * @param restaurant Le restaurant avec les nouvelles informations.
    * @throws EntityNotFoundException Si le restaurant n'est pas trouvé.
    */
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

    /**
    * Enregistre le restaurant passé en paramètre dans la base de données via le repository.
    * Cette méthode appelle la méthode {@code save} du {@code restaurantRepo} pour enregistrer
    * le restaurant et retourner l'objet mis à jour, incluant notamment l'ID généré par la base de données.
    *
    * @param restaurant l'objet Restaurant à enregistrer
    * @return le Restaurant enregistré avec son ID attribué
    */
    public Restaurant enregistrerRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);// Renvoie l'objet Restaurant enregistré, incluant l'ID généré
    }

    /**
    * Récupère la liste de tous les restaurants.
    * @return La liste de tous les restaurants.
    */
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

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

    /**
    * Supprime un restaurant par son identifiant.
    * @param id L'identifiant du restaurant à supprimer.
    * @throws EntityNotFoundException Si le restaurant n'est pas trouvé.
    */
    public void supprimerRestaurant(Long id){
        if (restaurantRepo.existsById(id)) {
            restaurantRepo.deleteById(id);
        } else {
        throw new EntityNotFoundException("Restaurant introuvable");
        }
    }
}


