package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entité représentant un gérant dans le système.
 * Cette classe hérite de la classe Utilisateur et est utilisée pour définir des utilisateurs avec des privilèges de gérant.
 */
@Entity
@DiscriminatorValue("GERANT")
public class Gerant extends Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Constructeur par défaut pour l'entité Gerant.
     */
    public Gerant() {
    }

    /**
     * Constructeur pour créer un nouveau gérant avec les détails spécifiés.
     *
     * @param nom Le nom du gérant.
     * @param prenom Le prénom du gérant.
     * @param email L'email du gérant.
     * @param motDePasse Le mot de passe du gérant.
     */
    public Gerant(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }

    /**
     * Obtient l'identifiant unique du gérant.
     *
     * @return L'identifiant unique du gérant.
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du gérant.
     *
     * @param id L'identifiant unique à définir pour le gérant.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
