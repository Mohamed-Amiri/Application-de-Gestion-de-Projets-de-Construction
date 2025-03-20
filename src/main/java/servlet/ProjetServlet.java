package servlet;

import dao.ProjetDAO;
import model.Projet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ProjetServlet")
public class ProjetServlet extends HttpServlet {
    private ProjetDAO projetDAO = new ProjetDAO();

    // Gestion des requêtes POST pour ajouter, modifier ou supprimer un projet
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("ajouter".equals(action)) {
                // Ajout d'un projet
                String nom = request.getParameter("nom");
                String description = request.getParameter("description");
                String budgetStr = request.getParameter("budget");
                double budget = (budgetStr != null && !budgetStr.trim().isEmpty()) ? Double.parseDouble(budgetStr) : 0.0;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateDebut = null;
                Date dateFin = null;

                String dateDebutStr = request.getParameter("dateDebut");
                String dateFinStr = request.getParameter("dateFin");

                try {
                    if (dateDebutStr != null && !dateDebutStr.trim().isEmpty()) {
                        dateDebut = sdf.parse(dateDebutStr);
                    }
                    if (dateFinStr != null && !dateFinStr.trim().isEmpty()) {
                        dateFin = sdf.parse(dateFinStr);
                    }
                } catch (ParseException e) {
                    throw new ServletException("Erreur lors de la conversion des dates", e);
                }

                Projet projet = new Projet(0, nom, description, dateDebut, dateFin, budget);
                projetDAO.ajouterProjet(projet);
                response.sendRedirect("Project.jsp");

            } else if ("modifier".equals(action)) {
                // Modification d'un projet
                int id = Integer.parseInt(request.getParameter("id"));
                String nom = request.getParameter("nom");
                String description = request.getParameter("description");
                String budgetStr = request.getParameter("budget");
                double budget = (budgetStr != null && !budgetStr.trim().isEmpty()) ? Double.parseDouble(budgetStr) : 0.0;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateDebut = null;
                Date dateFin = null;

                String dateDebutStr = request.getParameter("dateDebut");
                String dateFinStr = request.getParameter("dateFin");

                try {
                    if (dateDebutStr != null && !dateDebutStr.trim().isEmpty()) {
                        dateDebut = sdf.parse(dateDebutStr);
                    }
                    if (dateFinStr != null && !dateFinStr.trim().isEmpty()) {
                        dateFin = sdf.parse(dateFinStr);
                    }
                } catch (ParseException e) {
                    throw new ServletException("Erreur lors de la conversion des dates", e);
                }

                Projet projet = new Projet(id, nom, description, dateDebut, dateFin, budget);
                projetDAO.modifierProjet(projet); // Assurez-vous que cette méthode est bien implémentée

                response.sendRedirect("Project.jsp");

            } else if ("supprimer".equals(action)) {
                // Suppression d'un projet
                int id = Integer.parseInt(request.getParameter("id"));
                projetDAO.supprimerProjet(id); // Assurez-vous que cette méthode est bien implémentée
                response.sendRedirect("Project.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // Gestion des requêtes GET pour afficher les projets et charger un projet à modifier
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("modifier".equals(action)) {
                // Récupérer le projet pour modification
                int id = Integer.parseInt(request.getParameter("id"));
                Projet projet = projetDAO.getProjetParId(id); // Assurez-vous que cette méthode est bien implémentée dans ProjetDAO

                if (projet != null) {
                    request.setAttribute("projet", projet);
                    request.getRequestDispatcher("ModifierProjet.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Project.jsp");
                }

            } else {
                // Afficher tous les projets
                List<Projet> projets = projetDAO.getTousLesProjets();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Project.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
