package fr.gourmetgo.entity;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

import java.util.Objects;

import jakarta.persistence.Column;

/**
 * Classe abstraite représentant un utilisateur dans le système.
 * Cette classe est la base pour différents types d'utilisateurs tels que les administrateurs, les gérants et les clients.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_utilisateur")
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    /**
     * Constructeur par défaut pour l'entité Utilisateur.
     * Nécessaire pour JPA.
     */
    public Utilisateur() {
    }

    /**
     * Constructeur pour créer un nouvel utilisateur avec les détails spécifiés.
     *
     * @param nom Le nom de l'utilisateur.
     * @param prenom Le prénom de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     */
    public Utilisateur(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    /**
     * Obtient l'identifiant unique de l'utilisateur.
     *
     * @return L'identifiant unique de l'utilisateur.
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur.
     *
     * @param id L'identifiant unique à définir pour l'utilisateur.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtient le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param nom Le nom à définir pour l'utilisateur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le prénom de l'utilisateur.
     *
     * @return Le prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     *
     * @param prenom Le prénom à définir pour l'utilisateur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Obtient l'email de l'utilisateur.
     *
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'utilisateur.
     *
     * @param email L'email à définir pour l'utilisateur.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtient le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param motDePasse Le mot de passe à définir pour l'utilisateur.
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Méthode pour obtenir le type d'utilisateur.
     *
     * @return Le type d'utilisateur sous forme de chaîne de caractères.
     * @throws IllegalArgumentException Si le type d'utilisateur est inconnu.
     */
    public String getTypeUtilisateur() {
        if (this instanceof Administrateur) {
            return "ADMIN";
        } else if (this instanceof Gerant) {
            return "GERANT";
        } else if (this instanceof Client) {
            return "CLIENT";
        } else {
            throw new IllegalArgumentException("Unknown user type");
        }
    }

    /**
     * Compare cet utilisateur à un autre objet pour l'égalité.
     * Les utilisateurs sont considérés comme égaux si leurs noms, prénoms, emails et mots de passe sont égaux.
     *
     * @param o L'objet à comparer avec cet utilisateur.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(nom, that.nom) &&
               Objects.equals(prenom, that.prenom) &&
               Objects.equals(email, that.email) &&
               Objects.equals(motDePasse, that.motDePasse);
    }

    /**
     * Génère un code de hachage pour cet utilisateur.
     *
     * @return Un code de hachage pour cet utilisateur.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom, email, motDePasse);
    }
}
