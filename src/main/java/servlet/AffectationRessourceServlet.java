package servlet;

import dao.DAOFactory;
import dao.AffectationRessourceDAO;
import dao.RessourceDAO;
import dao.TacheDAO;
import model.AffectationRessource;
import model.Ressource;
import model.Tache;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/affectations/*")
public class AffectationRessourceServlet extends HttpServlet {
    private AffectationRessourceDAO affectationDAO;
    private RessourceDAO ressourceDAO;
    private TacheDAO tacheDAO;

    @Override
    public void init() throws ServletException {
        affectationDAO = DAOFactory.getAffectationRessourceDAO();
        ressourceDAO = DAOFactory.getRessourceDAO();
        tacheDAO = DAOFactory.getTacheDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all resource assignments
            List<AffectationRessource> affectations = affectationDAO.findAll();
            request.setAttribute("affectations", affectations);
            request.getRequestDispatcher("/WEB-INF/views/affectation/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/new")) {
            // Display form to create a new resource assignment
            List<Ressource> ressources = ressourceDAO.findAll();
            List<Tache> taches = tacheDAO.findAll();
            request.setAttribute("ressources", ressources);
            request.setAttribute("taches", taches);
            request.getRequestDispatcher("/WEB-INF/views/affectation/form.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Display form to edit an existing resource assignment
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                AffectationRessource affectation = affectationDAO.findById(id);

                if (affectation != null) {
                    List<Ressource> ressources = ressourceDAO.findAll();
                    List<Tache> taches = tacheDAO.findAll();
                    request.setAttribute("affectation", affectation);
                    request.setAttribute("ressources", ressources);
                    request.setAttribute("taches", taches);
                    request.getRequestDispatcher("/WEB-INF/views/affectation/form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/tache/")) {
            // List resource assignments for a specific task
            try {
                int tacheId = Integer.parseInt(pathInfo.substring(7));
                Tache tache = tacheDAO.findById(tacheId);

                if (tache != null) {
                    List<AffectationRessource> affectations = affectationDAO.findByTache(tache);
                    request.setAttribute("affectations", affectations);
                    request.setAttribute("tache", tache);
                    request.getRequestDispatcher("/WEB-INF/views/affectation/list.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/ressource/")) {
            // List resource assignments for a specific resource
            try {
                int ressourceId = Integer.parseInt(pathInfo.substring(11));
                Ressource ressource = ressourceDAO.findById(ressourceId);

                if (ressource != null) {
                    List<AffectationRessource> affectations = affectationDAO.findByRessource(ressource);
                    request.setAttribute("affectations", affectations);
                    request.setAttribute("ressource", ressource);
                    request.getRequestDispatcher("/WEB-INF/views/affectation/list.jsp").forward(request, response);
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
            // Create a new resource assignment
            try {
                AffectationRessource affectation = extractAffectationFromRequest(request);

                // Check if resource quantity is sufficient
                Ressource ressource = affectation.getRessource();
                if (ressource.getQuantite() < affectation.getQuantite_utilisee()) {
                    throw new IllegalArgumentException("Insufficient resource quantity available");
                }

                affectationDAO.save(affectation);
                response.sendRedirect(request.getContextPath() + "/affectations/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error creating resource assignment: " + e.getMessage());
                List<Ressource> ressources = ressourceDAO.findAll();
                List<Tache> taches = tacheDAO.findAll();
                request.setAttribute("ressources", ressources);
                request.setAttribute("taches", taches);
                request.getRequestDispatcher("/WEB-INF/views/affectation/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/edit/")) {
            // Update an existing resource assignment
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                AffectationRessource oldAffectation = affectationDAO.findById(id);
                AffectationRessource newAffectation = extractAffectationFromRequest(request);

                // Check if resource quantity is sufficient for the update
                Ressource ressource = newAffectation.getRessource();
                int availableQuantity = ressource.getQuantite();

                // If updating the same resource, add back the old quantity
                if (oldAffectation.getRessource().getId_ressource().equals(ressource.getId_ressource())) {
                    availableQuantity += oldAffectation.getQuantite_utilisee();
                }

                if (availableQuantity < newAffectation.getQuantite_utilisee()) {
                    throw new IllegalArgumentException("Insufficient resource quantity available");
                }

                newAffectation.setId_affectation(id);
                affectationDAO.update(newAffectation);
                response.sendRedirect(request.getContextPath() + "/affectations/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error updating resource assignment: " + e.getMessage());
                List<Ressource> ressources = ressourceDAO.findAll();
                List<Tache> taches = tacheDAO.findAll();
                request.setAttribute("ressources", ressources);
                request.setAttribute("taches", taches);
                request.getRequestDispatcher("/WEB-INF/views/affectation/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete a resource assignment
            try {
                int id = Integer.parseInt(pathInfo.substring(8));
                affectationDAO.deleteById(id);
                response.sendRedirect(request.getContextPath() + "/affectations/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error deleting resource assignment: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/affectations/");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private AffectationRessource extractAffectationFromRequest(HttpServletRequest request) {
        int ressourceId = Integer.parseInt(request.getParameter("ressourceId"));
        int tacheId = Integer.parseInt(request.getParameter("tacheId"));
        int quantite = Integer.parseInt(request.getParameter("quantite"));

        Ressource ressource = ressourceDAO.findById(ressourceId);
        Tache tache = tacheDAO.findById(tacheId);

        return new AffectationRessource(ressource, tache, quantite);
    }
}