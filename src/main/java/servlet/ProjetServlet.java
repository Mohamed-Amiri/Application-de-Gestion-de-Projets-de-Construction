package servlet;

import dao.DAOFactory;
import dao.ProjetDAO;
import model.Projet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/projets/*")
public class ProjetServlet extends HttpServlet {
    private ProjetDAO projetDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        projetDAO = DAOFactory.getProjetDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all projects
            List<Projet> projets = projetDAO.findAll();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("/WEB-INF/views/projet/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/new")) {
            // Display form to create a new project
            request.getRequestDispatcher("/WEB-INF/views/projet/form.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Display form to edit an existing project
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                Projet projet = projetDAO.findById(id);

                if (projet != null) {
                    request.setAttribute("projet", projet);
                    request.getRequestDispatcher("/WEB-INF/views/projet/form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/details/")) {
            // Display project details
            try {
                int id = Integer.parseInt(pathInfo.substring(9));
                Projet projet = projetDAO.findById(id);

                if (projet != null) {
                    request.setAttribute("projet", projet);
                    request.getRequestDispatcher("/WEB-INF/views/projet/details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/new")) {
            // Create a new project
            try {
                Projet projet = extractProjetFromRequest(request);
                projetDAO.save(projet);
                response.sendRedirect(request.getContextPath() + "/projets/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error creating project: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/projet/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/edit/")) {
            // Update an existing project
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                Projet projet = extractProjetFromRequest(request);
                projet.setId_projet(id);
                projetDAO.update(projet);
                response.sendRedirect(request.getContextPath() + "/projets/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error updating project: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/views/projet/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete a project
            try {
                int id = Integer.parseInt(pathInfo.substring(8));
                projetDAO.deleteById(id);
                response.sendRedirect(request.getContextPath() + "/projets/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error deleting project: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/projets/");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Projet extractProjetFromRequest(HttpServletRequest request) throws ParseException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        Date dateDebut = dateFormat.parse(request.getParameter("dateDebut"));
        Date dateFin = dateFormat.parse(request.getParameter("dateFin"));
        BigDecimal budget = new BigDecimal(request.getParameter("budget"));

        return new Projet(nom, description, dateDebut, dateFin, budget);
    }
}
