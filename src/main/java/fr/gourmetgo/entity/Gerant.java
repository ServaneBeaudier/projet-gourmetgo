package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GERANT")
public class Gerant extends Utilisateur{

    public Gerant(){
    }

       public Gerant(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }
}
