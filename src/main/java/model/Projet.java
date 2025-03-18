package model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "projet")
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_projet;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date date_debut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date date_fin;

    @Column(name = "budget")
    private BigDecimal budget;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches;

    // Constructeur par défaut
    public Projet() {}

    // Constructeurs avec paramètres
    public Projet(String nom, String description, Date date_debut, Date date_fin, BigDecimal budget) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.budget = budget;
    }

    // Getters & Setters
    public Integer getId_projet() {
        return id_projet;
    }

    public void setId_projet(Integer id_projet) {
        this.id_projet = id_projet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }
}