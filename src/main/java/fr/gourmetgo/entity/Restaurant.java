package fr.gourmetgo.entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Représente un restaurant dans le système.
 *
 * Cette classe contient les informations de base d'un restaurant, telles que son nom, son adresse,
 * ses horaires d'ouverture et de fermeture, son numéro de téléphone, son type, et une image associée.
 * Elle est annotée avec des contraintes de validation pour garantir l'intégrité des données.
 */
@Entity
@Table(
    name = "restaurants",
    uniqueConstraints = @UniqueConstraint(columnNames = {"nomResto", "numRue", "nomRue", "codePostal", "ville"})
)

public class Restaurant {

    /** L'identifiant unique du restaurant. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * Le nom du restaurant.
    * Ne peut pas être vide.
    */
    @NotBlank(message = "Veuillez renseigner le nom du restaurant")
    private String nomResto;

    /**
    * Le numéro de la rue du restaurant.
    * Ne peut pas être vide.
    */
    @NotBlank(message = "Veuillez renseigner le numéro")
    private String numRue;
    
    /**
    * Le nom de la rue du restaurant.
    * Ne peut pas être vide.
    */
    @NotBlank(message = "Veuillez renseigner la rue")
    private String nomRue;
    
    /**
    * Le code postal du restaurant.
    * Ne peut pas être vide.
    */
    @NotBlank(message = "Veuillez renseigner le code postal")
    private String codePostal;

    /**
    * La ville du restaurant.
    * Ne peut pas être vide.
    */
    @NotBlank(message = "Veuillez renseigner la ville")
    private String ville;

    /**
    * L'horaire d'ouverture du restaurant.
    * Ne peut pas être null.
    */
    @NotNull(message = "Veuillez renseigner l'horaire d'ouverture")
    private LocalTime heureOuverture;

    /**
    * L'horaire de fermeture du restaurant.
    * Ne peut pas être null.
    */
    @NotNull(message = "Veuillez renseigner l'horaire de fermeture")
    private LocalTime heureFermeture;

    /**
    * Le numéro de téléphone du restaurant.
    * Ne peut pas être vide et doit respecter le format français.
    */
    @NotBlank(message = "Veuillez renseigner le numéro de téléphone du restaurant")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Le numéro de téléphone doit contenir 10 chiffres et commencer par 0.")
    private String telResto;

    /**
    * Le type du restaurant.
    * Ne peut pas être vide.
    */
    @NotBlank(message = "Veuillez renseigner le type du restaurant.")
    private String typeResto;

    /** Autre type de restaurant qui ne serait pas dans le menu déroulant */
    private String autreType;

    /** L'image associée au restaurant. */
    private String imageResto;

    
    /** Le gérant du restaurant. */
    @OneToOne
    @JoinColumn(name = "gerant_id")
    private Gerant gerant;

    /** Constructeur par défaut */
    public Restaurant() {
    }

    /**
     * Constructeur avec paramètres.
     * @param nomResto Le nom du restaurant.
     * @param telResto Le numéro de téléphone du restaurant.
     * @param typeResto Le type du restaurant.
     */
    public Restaurant(String nomResto, String telResto, String typeResto) {
        this.nomResto = nomResto;
        this.numRue = numRue;
        this.nomRue = nomRue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.imageResto = imageResto;
        this.telResto = telResto;
        this.typeResto = typeResto;
    }

    /**
    * Retourne le nom du restaurant.
    * @return Le nom du restaurant.
    */
    public String getNomResto() {
        return nomResto;
    }

    /**
    * Définit le nom du restaurant.
    * @param nomResto Le nom du restaurant.
    */
    public void setNomResto(String nomResto) {
        this.nomResto = nomResto;
    }

    /**
    * Retourne l'horaire d'ouverture du restaurant.
    * @return L'horaire d'ouverture du restaurant.
    */
    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    /**
    * Définit l'horaire d'ouverture du restaurant.
    * @param heureOuverture L'horaire d'ouverture du restaurant.
    */
    public void setHeureOuverture(LocalTime heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    /**
    * Retourne l'horaire de fermeture du restaurant.
    * @return L'horaire de fermeture du restaurant.
    */
    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }
    
    /**
    * Définit l'horaire de fermeture du restaurant.
    * @param heureFermeture L'horaire de fermeture du restaurant.
    */
    public void setHeureFermeture(LocalTime heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    /**
    * Retourne le type du restaurant.
    * @return Le type du restaurant.
    */
    public String getTypeResto() {
        return typeResto;
    }

    /**
    * Définit le type du restaurant.
    * @param typeResto Le type du restaurant.
    */
    public void setTypeResto(String typeResto) {
        this.typeResto = typeResto;
    }

    /**
    * Retourne l'image associée au restaurant.
    * @return L'image associée au restaurant.
    */
    public String getImageResto() {
        return imageResto;
    }

    /**
    * Définit l'image associée au restaurant.
    * @param imageResto L'image associée au restaurant.
    */
    public void setImageResto(String imageResto) {
        this.imageResto = imageResto;
    }

    /**
    * Retourne le numéro de rue du restaurant.
    * @return Le numéro de rue du restaurant.
    */
    public String getNumRue() {
        return numRue;
    }

    /**
    * Définit le numéro de rue du restaurant.
    * @param numRue Le numéro de rue du restaurant.
    */
    public void setNumRue(String numRue) {
        this.numRue = numRue;
    }

    /**
    * Retourne le nom de la rue du restaurant.
    * @return Le nom de la rue du restaurant.
    */
    public String getNomRue() {
        return nomRue;
    }

    /**
    * Définit le nom de la rue du restaurant.
    * @param nomRue Le nom de la rue du restaurant.
    */
    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    /**
    * Définit le nom de la rue du restaurant.
    * @param nomRue Le nom de la rue du restaurant.
    */
    public String getCodePostal() {
        return codePostal;
    }

    /**
    * Définit le code postal du restaurant.
    * @param codePostal Le code postal du restaurant.
    */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
    * Retourne la ville du restaurant.
    * @return La ville du restaurant.
    */
    public String getVille() {
        return ville;
    }

    /**
    * Définit la ville du restaurant.
    * @param ville La ville du restaurant.
    */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
    * Retourne le numéro de téléphone du restaurant.
    * @return Le numéro de téléphone du restaurant.
    */
    public String getTelResto() {
        return telResto;
    }

    /**
    * Définit le numéro de téléphone du restaurant.
    * @param telResto Le numéro de téléphone du restaurant.
    */
    public void setTelResto(String telResto) {
        this.telResto = telResto;
    }

    /**
    * Retourne le gérant du restaurant.
    * @return Le gérant du restaurant.
    */
    public Gerant getGerant() {
        return gerant;
    }

    /**
    * Définit le gérant du restaurant.
    * @param gerant Le gérant du restaurant.
    */
    public void setGerant(Gerant gerant) {
        this.gerant = gerant;
    }

    /**
    * Retourne l'identifiant unique du restaurant.
    * @return L'identifiant unique du restaurant.
    */
    public Long getId() {
        return id;
    }

    /**
    * Définit l'identifiant unique du restaurant.
    * @param id L'identifiant unique du restaurant.
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * Retourne un autre type de restaurant.
    * @return Un autre type de restaurant.
    */
    public String getAutreType() {
        return autreType;
    }

    /**
    * Définit un autre type de restaurant.
    * @param autreType Un autre type de restaurant.
    */
    public void setAutreType(String autreType) {
        this.autreType = autreType;
    }

    
    
    

    
}





   