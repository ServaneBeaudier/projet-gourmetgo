package fr.gourmetgo.repository;


import fr.gourmetgo.entite.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface AppRepository extends JpaRepository<Restaurant, Long> {
}



