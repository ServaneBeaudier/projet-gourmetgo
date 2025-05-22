package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
@DiscriminatorValue("GERANT")
public class Gerant extends Utilisateur{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Gerant(){
    }
    
    public Gerant(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}