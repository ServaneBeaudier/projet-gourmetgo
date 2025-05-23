package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Entité représentant un administrateur dans le système.
 * Cette classe hérite de la classe Utilisateur et est utilisée pour définir des utilisateurs avec des privilèges d'administrateur.
 */
@Entity
@DiscriminatorValue("ADMIN")
public class Administrateur extends Utilisateur {

    /**
     * Constructeur par défaut pour l'entité Administrateur.
     */
    public Administrateur() {
    }

    /**
     * Constructeur pour créer un nouvel administrateur avec les détails spécifiés.
     *
     * @param nom Le nom de l'administrateur.
     * @param prenom Le prénom de l'administrateur.
     * @param email L'email de l'administrateur.
     * @param motDePasse Le mot de passe de l'administrateur.
     */
    public Administrateur(String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
    }
}
