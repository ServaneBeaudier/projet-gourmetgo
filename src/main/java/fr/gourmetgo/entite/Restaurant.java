package fr.gourmetgo.entite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResto;

    @NotBlank(message = "Veuillez renseigner le nom du restaurant")
    private String nomResto;

    @NotBlank(message = "Veuillez renseigner l'adresse du restaurant")
    private String adresse;

    @NotBlank(message = "Veuillez renseigner les horaires d'ouverture du restaurant")
    private String horaires;

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
        this.adresse = adresse;
        this.horaires = horaires;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getHoraires() {
        return horaires;
    }

    public void setHoraires(String horaire) {
        this.horaires = horaire;
    }

    public String getTelResto() {
        return telResto;
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

 

    

    
}





   