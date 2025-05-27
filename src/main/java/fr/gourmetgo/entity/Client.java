package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entité représentant un client dans le système.
 * Cette classe hérite de la classe Utilisateur et est utilisée pour définir des utilisateurs avec des privilèges de client.
 * Les clients ont généralement accès à des fonctionnalités spécifiques pour interagir avec les services proposés par le système.
 */
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur {

    /**
     * Constructeur par défaut pour l'entité Client.
     * Nécessaire pour JPA.
     */
    public Client() {
    }

    /**
     * Constructeur pour créer un nouveau client avec les détails spécifiés.
     *
     * @param nom Le nom du client.
     * @param prenom Le prénom du client.
     * @param email L'email du client.
     * @param motDePasse Le mot de passe du client.
     */
    public Client(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }

    // PAS de champ id ici — il est hérité de Utilisateur
}
