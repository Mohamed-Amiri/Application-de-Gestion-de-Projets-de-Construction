package servlet;

import dao.DAOFactory;
import dao.ProjetDAO;
import dao.TacheDAO;
import model.Projet;
import model.Tache;
import model.Tache.EtatTache;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/taches/*")
public class TacheServlet extends HttpServlet {
    private TacheDAO tacheDAO;
    private ProjetDAO projetDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws ServletException {
        tacheDAO = DAOFactory.getTacheDAO();
        projetDAO = DAOFactory.getProjetDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all tasks
            List<Tache> taches = tacheDAO.findAll();
            request.setAttribute("taches", taches);
            request.getRequestDispatcher("/WEB-INF/views/tache/list.jsp").forward(request, response);
        } else if (pathInfo.equals("/new")) {
            // Display form to create a new task
            List<Projet> projets = projetDAO.findAll();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("/WEB-INF/views/tache/form.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Display form to edit an existing task
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                Tache tache = tacheDAO.findById(id);

                if (tache != null) {
                    List<Projet> projets = projetDAO.findAll();
                    request.setAttribute("projets", projets);
                    request.setAttribute("tache", tache);
                    request.getRequestDispatcher("/WEB-INF/views/tache/form.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/details/")) {
            // Display task details
            try {
                int id = Integer.parseInt(pathInfo.substring(9));
                Tache tache = tacheDAO.findById(id);

                if (tache != null) {
                    request.setAttribute("tache", tache);
                    request.getRequestDispatcher("/WEB-INF/views/tache/details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else if (pathInfo.startsWith("/projet/")) {
            // List tasks for a specific project
            try {
                int projetId = Integer.parseInt(pathInfo.substring(8));
                Projet projet = projetDAO.findById(projetId);

                if (projet != null) {
                    List<Tache> taches = tacheDAO.findByProjet(projet);
                    request.setAttribute("taches", taches);
                    request.setAttribute("projet", projet);
                    request.getRequestDispatcher("/WEB-INF/views/tache/list.jsp").forward(request, response);
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
            // Create a new task
            try {
                Tache tache = extractTacheFromRequest(request);
                tacheDAO.save(tache);
                response.sendRedirect(request.getContextPath() + "/taches/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error creating task: " + e.getMessage());
                List<Projet> projets = projetDAO.findAll();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("/WEB-INF/views/tache/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/edit/")) {
            // Update an existing task
            try {
                int id = Integer.parseInt(pathInfo.substring(6));
                Tache tache = extractTacheFromRequest(request);
                tache.setId_tache(id);
                tacheDAO.update(tache);
                response.sendRedirect(request.getContextPath() + "/taches/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error updating task: " + e.getMessage());
                List<Projet> projets = projetDAO.findAll();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("/WEB-INF/views/tache/form.jsp").forward(request, response);
            }
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete a task
            try {
                int id = Integer.parseInt(pathInfo.substring(8));
                tacheDAO.deleteById(id);
                response.sendRedirect(request.getContextPath() + "/taches/");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error deleting task: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/taches/");
            }
        } else if (pathInfo.startsWith("/updateStatus/")) {
            // Update task status
            try {
                int id = Integer.parseInt(pathInfo.substring(14));
                Tache tache = tacheDAO.findById(id);

                if (tache != null) {
                    String statusParam = request.getParameter("status");
                    EtatTache newStatus = EtatTache.valueOf(statusParam);
                    tache.setEtat(newStatus);
                    tacheDAO.update(tache);
                    response.sendRedirect(request.getContextPath() + "/taches/details/" + id);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Error updating task status: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/taches/");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Tache extractTacheFromRequest(HttpServletRequest request) throws ParseException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        Date dateDebut = dateFormat.parse(request.getParameter("dateDebut"));
        Date dateFin = dateFormat.parse(request.getParameter("dateFin"));
        int projetId = Integer.parseInt(request.getParameter("projetId"));
        Projet projet = projetDAO.findById(projetId);

        Tache tache = new Tache(nom, description, dateDebut, dateFin, projet);

        // Set status if provided
        String etatParam = request.getParameter("etat");
        if (etatParam != null && !etatParam.isEmpty()) {
            tache.setEtat(EtatTache.valueOf(etatParam));
        }

        return tache;
    }
}