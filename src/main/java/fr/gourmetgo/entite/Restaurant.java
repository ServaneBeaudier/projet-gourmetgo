package fr.gourmetgo.entite;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(
    name = "restaurants",
    uniqueConstraints = @UniqueConstraint(columnNames = {"nomResto", "numRue", "nomRue", "codePostal", "ville"})
)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResto;

    @NotBlank(message = "Veuillez renseigner le nom du restaurant")
    private String nomResto;

    @NotBlank(message = "Veuillez renseigner le numéro")
    private String numRue;
    
    @NotBlank(message = "Veuillez renseigner la rue")
    private String nomRue;
    
    @NotBlank(message = "Veuillez renseigner le code postal")
    private String codePostal;
    
    @NotBlank(message = "Veuillez renseigner la ville")
    private String ville;


    @NotNull(message = "Veuillez renseigner l'horaire d'ouverture")
    private LocalTime heureOuverture;

    @NotNull(message = "Veuillez renseigner l'horaire de fermeture")
    private LocalTime heureFermeture;


    @NotBlank(message = "Veuillez renseigner le numéro de téléphone du restaurant")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Le numéro de téléphone doit contenir 10 chiffres et commencer par 0.")
    private String telResto;

    @NotBlank(message = "Veuillez renseigner le type du restaurant.")
    private String typeResto;
 
    @NotBlank(message = "Veuillez renseigner l'image.")
    private String imageResto;
    
    



    // Constructeurs
    public Restaurant() {
    }

    public Restaurant(Long idResto, String nomResto, String adresse, String horaire, String telResto, String typeResto,
            String statut) {
        this.idResto = idResto;
        this.nomResto = nomResto;
        this.numRue = numRue;
        this.nomRue = nomRue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.imageResto = imageResto;
        this.telResto = telResto;
        this.typeResto = typeResto;

    }



    // Getters & Setters


    public Long getIdResto() {
        return idResto;
    }

    public void setIdResto(Long idResto) {
        this.idResto = idResto;
    }

    public String getNomResto() {
        return nomResto;
    }

    public void setNomResto(String nomResto) {
        this.nomResto = nomResto;
    }

    public String getTelResto() {
        return telResto;
    }

    public LocalTime getHeureOuverture() {
        return heureOuverture;
    }

    public void setHeureOuverture(LocalTime heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public LocalTime getHeureFermeture() {
        return heureFermeture;
    }

    public void setHeureFermeture(LocalTime heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public void setTelResto(String telResto) {
        this.telResto = telResto;
    }

    public String getTypeResto() {
        return typeResto;
    }

    public void setTypeResto(String typeResto) {
        this.typeResto = typeResto;
    }

    public String getImageResto() {
        return imageResto;
    }

    public void setImageResto(String imageResto) {
        this.imageResto = imageResto;
    }

    public String getNumRue() {
        return numRue;
    }

    public void setNumRue(String numRue) {
        this.numRue = numRue;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

 

    

    
}





   