package fr.gourmetgo.repository; // Assurez-vous que le package est bien scanné

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import fr.gourmetgo.entite.Restaurant;

public interface AppRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByNomRestoAndAdresse(String nomResto, String adresse);
}





