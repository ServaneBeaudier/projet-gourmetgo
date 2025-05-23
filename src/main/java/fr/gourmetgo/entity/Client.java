package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entité représentant un client dans le système.
 * Cette classe hérite de la classe Utilisateur et est utilisée pour définir des utilisateurs avec des privilèges de client.
 */
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Constructeur par défaut pour l'entité Client.
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

    /**
     * Obtient l'identifiant unique du client.
     *
     * @return L'identifiant unique du client.
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du client.
     *
     * @param id L'identifiant unique à définir pour le client.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
