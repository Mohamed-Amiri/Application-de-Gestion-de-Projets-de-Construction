package model;

import java.util.Date;

public class Tache {
    private int id;
    private int idProjet;
    private String nom;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private String etat;

    public Tache() {}

    public Tache(int id, int idProjet, String nom, String description, Date dateDebut, Date dateFin, String etat) {
        this.id = id;
        this.idProjet = idProjet;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etat = etat;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProjet() { return idProjet; }
    public void setIdProjet(int idProjet) { this.idProjet = idProjet; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}