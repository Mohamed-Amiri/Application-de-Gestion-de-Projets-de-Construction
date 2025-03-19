package dao;

import model.Ressource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RessourceDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/construction";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    public void ajouterRessource(Ressource ressource) throws SQLException {
        String query = "INSERT INTO Ressource (nom, type, quantite, fournisseur, coutUnitaire) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, ressource.getNom());
            ps.setString(2, ressource.getType());
            ps.setInt(3, ressource.getQuantite());
            ps.setString(4, ressource.getFournisseur());
            ps.setDouble(5, ressource.getCoutUnitaire());
            ps.executeUpdate();
        }
    }

    public List<Ressource> getToutesLesRessources() throws SQLException {
        List<Ressource> ressources = new ArrayList<>();
        String query = "SELECT * FROM Ressource";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Ressource r = new Ressource(rs.getInt("id"), rs.getString("nom"), rs.getString("type"),
                        rs.getInt("quantite"), rs.getString("fournisseur"), rs.getDouble("coutUnitaire"));
                ressources.add(r);
            }
        }
        return ressources;
    }
}
