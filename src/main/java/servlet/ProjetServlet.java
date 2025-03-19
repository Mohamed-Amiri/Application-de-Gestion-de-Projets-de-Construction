package servlet;

import dao.ProjetDAO;
import model.Projet;

import javax.servlet.RequestDispatcher;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            try {
                String nom = request.getParameter("nom");
                String description = request.getParameter("description");
                double budget = Double.parseDouble(request.getParameter("budget"));
                Projet projet = new Projet(0, nom, description, null, null, budget);
                projetDAO.ajouterProjet(projet);
                response.sendRedirect("Project.jsp");
            } catch (SQLException e) {
                throw new ServletException(e);
            }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("Projet.jsp");
        rd.forward(request, response);



            try {
                List<Projet> projets = projetDAO.getTousLesProjets();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Project.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }


