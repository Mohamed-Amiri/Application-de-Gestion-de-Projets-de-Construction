package dao;

import model.Tache;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/construction";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    public void ajouterTache(Tache tache) throws SQLException {
        String query = "INSERT INTO Tache (idProjet, nom, description, dateDebut, dateFin, etat) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, tache.getIdProjet());
            ps.setString(2, tache.getNom());
            ps.setString(3, tache.getDescription());
            ps.setDate(4, new java.sql.Date(tache.getDateDebut().getTime()));
            ps.setDate(5, new java.sql.Date(tache.getDateFin().getTime()));
            ps.setString(6, tache.getEtat());
            ps.executeUpdate();
        }
    }

    public List<Tache> getToutesLesTaches() throws SQLException {
        List<Tache> taches = new ArrayList<>();
        String query = "SELECT * FROM Tache";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Tache t = new Tache(rs.getInt("id"), rs.getInt("idProjet"), rs.getString("nom"), rs.getString("description"),
                        rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getString("etat"));
                taches.add(t);
            }
        }
        return taches;
    }
}
