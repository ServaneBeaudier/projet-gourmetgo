package fr.gourmetgo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Gerant extends Utilisateur{

    @OneToOne(mappedBy = "gerant", cascade = CascadeType.ALL)
    private Restaurant restaurant;

    public Gerant(){
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    
}
