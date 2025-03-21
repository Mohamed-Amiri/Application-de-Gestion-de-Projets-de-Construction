package dao;

import model.Tache;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/construction";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    // Créer une tâche
    public void ajouterTache(Tache tache) throws SQLException {
        String query = "INSERT INTO Tache (idProjet, nom, description, dateDebut, dateFin, etat) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            setTacheParameters(ps, tache);
            ps.executeUpdate();
        }
    }

    // Lire toutes les tâches
    public List<Tache> getToutesLesTaches() throws SQLException {
        return executeTacheQuery("SELECT * FROM Tache");
    }

    // Lire une tâche par ID
    public Tache getTacheParId(int id) throws SQLException {
        String query = "SELECT * FROM Tache WHERE id = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSetToTache(rs) : null;
            }
        }
    }

    // Lire les tâches par projet
    public List<Tache> getTachesParProjet(int idProjet) throws SQLException {
        return executeTacheQuery("SELECT * FROM Tache WHERE idProjet = ?", idProjet);
    }

    // Mettre à jour une tâche
    public void modifierTache(Tache tache) throws SQLException {
        String query = "UPDATE Tache SET idProjet=?, nom=?, description=?, dateDebut=?, dateFin=?, etat=? WHERE id=?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            setTacheParameters(ps, tache);
            ps.setInt(7, tache.getId());
            ps.executeUpdate();
        }
    }

    // Supprimer une tâche
    public void supprimerTache(int id) throws SQLException {
        String query = "DELETE FROM Tache WHERE id = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Méthodes utilitaires
    private void setTacheParameters(PreparedStatement ps, Tache tache) throws SQLException {
        ps.setInt(1, tache.getIdProjet());
        ps.setString(2, tache.getNom());
        ps.setString(3, tache.getDescription());
        ps.setDate(4, new java.sql.Date(tache.getDateDebut().getTime()));
        ps.setDate(5, new java.sql.Date(tache.getDateFin().getTime()));
        ps.setString(6, tache.getEtat());
    }

    private Tache mapResultSetToTache(ResultSet rs) throws SQLException {
        return new Tache(
                rs.getInt("id"),
                rs.getInt("idProjet"),
                rs.getString("nom"),
                rs.getString("description"),
                rs.getDate("dateDebut"),
                rs.getDate("dateFin"),
                rs.getString("etat")
        );
    }

    private List<Tache> executeTacheQuery(String baseQuery, int... params) throws SQLException {
        List<Tache> taches = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(baseQuery)) {

            for (int i = 0; i < params.length; i++) {
                ps.setInt(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    taches.add(mapResultSetToTache(rs));
                }
            }
        }
        return taches;
    }
}