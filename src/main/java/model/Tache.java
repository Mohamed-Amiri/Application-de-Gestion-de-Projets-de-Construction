package model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tache;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatTache etat = EtatTache.EN_ATTENTE;

    @ManyToOne
    @JoinColumn(name = "id_projet")
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL)
    private List<AffectationRessource> affectations;

    public Tache() {}

    public enum EtatTache {
        EN_ATTENTE,
        EN_COURS,
        TERMINEE
    }

    public Tache(String nom, String description, Date date_debut, Date date_fin, Projet projet) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.projet = projet;
        this.etat = EtatTache.EN_ATTENTE;
    }

    public Integer getId_tache() {
        return id_tache;
    }

    public void setId_tache(Integer id_tache) {
        this.id_tache = id_tache;
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

    public EtatTache getEtat() {
        return etat;
    }

    public void setEtat(EtatTache etat) {
        this.etat = etat;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<AffectationRessource> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<AffectationRessource> affectations) {
        this.affectations = affectations;
    }
}