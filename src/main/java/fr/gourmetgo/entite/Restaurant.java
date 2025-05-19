package fr.gourmetgo.entite;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "restaurantsgg")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResto;

    @NotBlank(message = "Veuillez renseigner le nom du restaurant")
    private String nomResto;

    @NotBlank(message = "Veuillez renseigner l'adresse du restaurant")
    private String adresse;

    @NotBlank(message = "Veuillez renseigner les horaires d'ouverture du restaurant")
    private String horaire;

    @NotBlank(message = "Veuillez renseigner le numéro de téléphone du restaurant")
    @Pattern(regexp = "^0[1-9][0-9]{8}$", message = "Le numéro de téléphone doit contenir 10 chiffres et commencer par 0.")
    private int telResto;

    @NotBlank(message = "Veuillez renseigner le type du restaurant.")
    private String typeResto;

    private String statut; // "VALIDÉ" ou "NON_VALIDE"


    

    // Constructeurs
    public Restaurant() {
    }

    public Restaurant(Long idResto, String nomResto, String adresse, String horaire, int telResto, String typeResto,
            String statut) {
        this.idResto = idResto;
        this.nomResto = nomResto;
        this.adresse = adresse;
        this.horaire = horaire;
        this.telResto = telResto;
        this.typeResto = typeResto;
        this.statut = statut;
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

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public int getTelResto() {
        return telResto;
    }

    public void setTelResto(int telResto) {
        this.telResto = telResto;
    }

    public String getTypeResto() {
        return typeResto;
    }

    public void setTypeResto(String typeResto) {
        this.typeResto = typeResto;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    
}





   