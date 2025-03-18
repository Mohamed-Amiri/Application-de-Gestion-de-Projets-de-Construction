package servlet;

import dao.DAOFactory;
import dao.RessourceDAO;
import model.Ressource;
import model.Ressource.TypeRessource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/ressources/*")
public class RessourceServlet extends HttpServlet {
    private RessourceDAO ressourceDAO;

    @Override
    public void init() throws ServletException {
        ressourceDAO = DAOFactory.getRessourceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all resources
            List<Ressource> ressources = ressourceDAO.findAll();
            request.setAttribute("ressources", ressources);
            request.getRequestDispatcher("/WEB-INF/views/ressource/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/new")) {
            // Display form to create a new resource
            request.setAttribute("types", TypeRessource.values());
            request.getRequestDispatcher("/WEB-INF/views/ressource/form.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Display form to edit an existing resource
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                Ressource ressource = ressourceDAO.findById(id);

                if (ressource != null) {
                    request.setAttribute("ressource", ressource);
                    request.setAttribute("types", TypeRessource.values());
                    request.getRequestDispatcher("/WEB-INF/views/ressource/form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/details/")) {
            // Display resource details
            try {
                int id = Integer.parseInt(pathInfo.substring(9));
                Ressource ressource = ressourceDAO.findById(id);

                if (ressource != null) {
                    request.setAttribute("ressource", ressource);
                    request.getRequestDispatcher("/WEB-INF/views/ressource/details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/type/")) {
            // List resources by type
            try {
                String typeStr = pathInfo.substring(6);
                TypeRessource type = TypeRessource.valueOf(typeStr);
                List<Ressource> ressources = ressourceDAO.findByType(type);
                request.setAttribute("ressources", ressources);
                request.setAttribute("resourceType", type);
                request.getRequestDispatcher("/WEB-INF/views/ressource/list.jsp").forward(request, response);
            } catch (IllegalArgumentException e) {
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
            // Create a new resource
            try {
                Ressource ressource = extractRessourceFromRequest(request);
                ressourceDAO.save(ressource);
                response.sendRedirect(request.getContextPath() + "/ressources/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error creating resource: " + e.getMessage());
                request.setAttribute("types", TypeRessource.values());
                request.getRequestDispatcher("/WEB-INF/views/ressource/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/edit/")) {
            // Update an existing resource
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                Ressource ressource = extractRessourceFromRequest(request);
                ressource.setId_ressource(id);
                ressourceDAO.update(ressource);
                response.sendRedirect(request.getContextPath() + "/ressources/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error updating resource: " + e.getMessage());
                request.setAttribute("types", TypeRessource.values());
                request.getRequestDispatcher("/WEB-INF/views/ressource/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete a resource
            try {
                int id = Integer.parseInt(pathInfo.substring(8));
                ressourceDAO.deleteById(id);
                response.sendRedirect(request.getContextPath() + "/ressources/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error deleting resource: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/ressources/");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Ressource extractRessourceFromRequest(HttpServletRequest request) {
        String nom = request.getParameter("nom");
        TypeRessource type = TypeRessource.valueOf(request.getParameter("type"));
        Integer quantite = Integer.parseInt(request.getParameter("quantite"));
        String fournisseur = request.getParameter("fournisseur");
        BigDecimal coutUnitaire = new BigDecimal(request.getParameter("coutUnitaire"));

        return new Ressource(nom, type, quantite, fournisseur, coutUnitaire);
    }
}