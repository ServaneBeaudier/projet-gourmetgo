package fr.gourmetgo.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adresse;
    private String nomResto;
    private String horaire;
    private int numeroTel;
    private String typeResto;

    public Restaurant() {
    }

    public Restaurant(String adresse, String nomResto, String horaire, int numeroTel, String typeResto) {
        this.adresse = adresse;
        this.nomResto = nomResto;
        this.horaire = horaire;
        this.numeroTel = numeroTel;
        this.typeResto = typeResto;
    }

    @OneToOne
    @JoinColumn(name = "gerant_id")
    private Gerant gerant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNomResto() {
        return nomResto;
    }

    public void setNomResto(String nomResto) {
        this.nomResto = nomResto;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public int getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(int numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getTypeResto() {
        return typeResto;
    }

    public void setTypeResto(String typeResto) {
        this.typeResto = typeResto;
    }

    public Gerant getGerant() {
        return gerant;
    }

    public void setGerant(Gerant gerant) {
        this.gerant = gerant;
    }

    
}
