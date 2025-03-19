package dao;

import model.Projet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/construction";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    public void ajouterProjet(Projet projet) throws SQLException {
        String query = "INSERT INTO Projet (nom, description, dateDebut, dateFin, budget) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, projet.getNom());
            ps.setString(2, projet.getDescription());
            ps.setDate(3, new java.sql.Date(projet.getDateDebut().getTime()));
            ps.setDate(4, new java.sql.Date(projet.getDateFin().getTime()));
            ps.setDouble(5, projet.getBudget());
            ps.executeUpdate();
        }
    }

    public List<Projet> getTousLesProjets() throws SQLException {
        List<Projet> projets = new ArrayList<>();
        String query = "SELECT * FROM projet";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Projet p = new Projet(rs.getInt("id_projet"), rs.getString("nom"), rs.getString("description"),
                        rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getDouble("budget"));
                projets.add(p);
            }
        }
        return projets;
    }

    public void modifierProjet(Projet projet) throws SQLException {
        String query = "UPDATE Projet SET nom=?, description=?, dateDebut=?, dateFin=?, budget=? WHERE id=?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, projet.getNom());
            ps.setString(2, projet.getDescription());
            ps.setDate(3, new java.sql.Date(projet.getDateDebut().getTime()));
            ps.setDate(4, new java.sql.Date(projet.getDateFin().getTime()));
            ps.setDouble(5, projet.getBudget());
            ps.setInt(6, projet.getId());
            ps.executeUpdate();
        }
    }

    public void supprimerProjet(int id) throws SQLException {
        String query = "DELETE FROM Projet WHERE id=?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}