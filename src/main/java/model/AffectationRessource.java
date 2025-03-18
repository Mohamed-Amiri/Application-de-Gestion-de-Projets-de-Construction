package model;

import jakarta.persistence.*;

@Entity
@Table(name = "affectation_ressource")
public class AffectationRessource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_affectation;

    @ManyToOne
    @JoinColumn(name = "id_ressource")
    private Ressource ressource;

    @ManyToOne
    @JoinColumn(name = "id_tache")
    private Tache tache;

    @Column(name = "quantite_utilisee")
    private Integer quantite_utilisee;

    // Constructeurs
    public AffectationRessource() {}

    public AffectationRessource(Ressource ressource, Tache tache, Integer quantite_utilisee) {
        this.ressource = ressource;
        this.tache = tache;
        this.quantite_utilisee = quantite_utilisee;
    }

    // Getters & Setters
    public Integer getId_affectation() {
        return id_affectation;
    }

    public void setId_affectation(Integer id_affectation) {
        this.id_affectation = id_affectation;
    }

    public Ressource getRessource() {
        return ressource;
    }

    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public Integer getQuantite_utilisee() {
        return quantite_utilisee;
    }

    public void setQuantite_utilisee(Integer quantite_utilisee) {
        this.quantite_utilisee = quantite_utilisee;
    }
}