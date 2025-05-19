package fr.gourmetgo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.gourmetgo.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository <Restaurant, Integer>{

}
