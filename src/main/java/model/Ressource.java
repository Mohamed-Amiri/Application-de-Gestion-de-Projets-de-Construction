package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;


public class Ressource {
    private Integer id_ressource;
    private String nom;
    private TypeRessource type;
    private Integer quantite;
    private String fournisseur;
    private BigDecimal cout_unitaire;
    private List<AffectationRessource> affectations;

    public enum TypeRessource {
        MATERIEL, MAIN_D_OEUVRE, OUTIL
    }

    public Ressource() {}

    public Ressource(String nom, TypeRessource type, Integer quantite, String fournisseur, BigDecimal cout_unitaire) {
        this.nom = nom;
        this.type = type;
        this.quantite = quantite;
        this.fournisseur = fournisseur;
        this.cout_unitaire = cout_unitaire;
    }

    // Getters & Setters
    public Integer getId_ressource() {
        return id_ressource;
    }

    public void setId_ressource(Integer id_ressource) {
        this.id_ressource = id_ressource;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeRessource getType() {
        return type;
    }

    public void setType(TypeRessource type) {
        this.type = type;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public BigDecimal getCout_unitaire() {
        return cout_unitaire;
    }

    public void setCout_unitaire(BigDecimal cout_unitaire) {
        this.cout_unitaire = cout_unitaire;
    }

    public List<AffectationRessource> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<AffectationRessource> affectations) {
        this.affectations = affectations;
    }
}

