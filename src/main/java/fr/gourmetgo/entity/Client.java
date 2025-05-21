package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur{

    public Client(){
    }

       public Client(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }
}

    
