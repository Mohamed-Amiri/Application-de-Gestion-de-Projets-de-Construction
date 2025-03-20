package dao;

import model.Projet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDAO {

    public void ajouterProjet(Projet projet) throws SQLException {
        String query = "INSERT INTO Projet (nom, description, date_debut, date_fin, budget) VALUES (?, ?, ?, ?, ?)";
        try (Connection con =ConnectToDB.getConnection();
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
        try (Connection con = ConnectToDB.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Projet p = new Projet();
                    p.setId(rs.getInt("id_projet"));
                    p.setNom(rs.getString("nom"));
                    p.setDescription(rs.getString("description"));
                    p.setDateDebut(rs.getDate("date_debut"));
                    p.setDateFin(rs.getDate("date_fin"));
                    p.setBudget(rs.getDouble("budget"));
                    projets.add(p);
            }
        }
        return projets;
    }

    public void modifierProjet(Projet projet) throws SQLException {
        String query = "UPDATE projet SET nom = ?, description = ?, date_debut = ?, date_fin = ?, budget = ? WHERE id_projet = ?";

        try (PreparedStatement ps = ConnectToDB.getConnection().prepareStatement(query)) {
            ps.setString(1, projet.getNom());
            ps.setString(2, projet.getDescription());

            // Vérifiez si dateDebut ou dateFin sont nulles avant de les mettre dans la requête
            if (projet.getDateDebut() != null) {
                ps.setDate(3, new java.sql.Date(projet.getDateDebut().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            if (projet.getDateFin() != null) {
                ps.setDate(4, new java.sql.Date(projet.getDateFin().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setDouble(5, projet.getBudget());
            ps.setInt(6, projet.getId());

            ps.executeUpdate();
        }
    }


    public void supprimerProjet(int id) throws SQLException {
        String query = "DELETE FROM Projet WHERE id_projet=?";
        try (Connection con = ConnectToDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public Projet getProjetParId(int id) throws SQLException {
        String sql = "SELECT * FROM projet WHERE id = ?";
        try (PreparedStatement statement = ConnectToDB.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Projet(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDate("dateDebut"),
                            rs.getDate("dateFin"),
                            rs.getDouble("budget")
                    );
                }
            }
        }
        return null; // Retourne null si aucun projet n'est trouvé
    }
}