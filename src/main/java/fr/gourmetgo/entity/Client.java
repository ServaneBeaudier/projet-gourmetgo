package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entité représentant un client dans le système.
 * Cette classe hérite de la classe Utilisateur et est utilisée pour définir des utilisateurs avec des privilèges de client.
 */
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur {

    public Client() {
    }

    public Client(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }

    // PAS de champ id ici — il est hérité de Utilisateur
}
