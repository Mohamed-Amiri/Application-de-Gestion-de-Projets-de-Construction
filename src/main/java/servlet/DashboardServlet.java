package servlet;

import dao.DAOFactory;
import dao.ProjetDAO;
import dao.TacheDAO;
import dao.RessourceDAO;
import model.Projet;
import model.Tache;
import model.Tache.EtatTache;
import model.Ressource;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private ProjetDAO projetDAO;
    private TacheDAO tacheDAO;
    private RessourceDAO ressourceDAO;

    @Override
    public void init() throws ServletException {
        projetDAO = DAOFactory.getProjetDAO();
        tacheDAO = DAOFactory.getTacheDAO();
        ressourceDAO = DAOFactory.getRessourceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get project stats
        long totalProjects = projetDAO.count();
        List<Projet> recentProjects = projetDAO.findAll(); // Ideally limited to recent ones
        long totalTasks =0;
        for(Projet projet : recentProjects) {
            totalTasks = tacheDAO.countByProjet(projet);
        }
        Map<EtatTache, Long> taskStatusCounts = new HashMap<>();
        for (EtatTache status : EtatTache.values()) {
            taskStatusCounts.put(status, tacheDAO.findByEtat(status));
        }

        // Calculate completion percentage
        long completedTasks = taskStatusCounts.getOrDefault(EtatTache.TERMINEE, 0L);
        double completionPercentage = (totalTasks > 0) ? (completedTasks * 100.0 / totalTasks) : 0;

        // Get resource stats
        long totalResources = ressourceDAO.count();
        Map<Ressource.TypeRessource, Long> resourceTypeCounts = new HashMap<>();
        for (Ressource.TypeRessource type : Ressource.TypeRessource.values()) {
            resourceTypeCounts.put(type, ressourceDAO.countByType(type));
        }

        // Get urgent tasks (tasks due soon)
        List<Tache> urgentTasks = tacheDAO.findUrgentTasks();

        // Get projects with approaching deadlines
        List<Projet> projectsWithApproachingDeadlines = projetDAO.findProjectsWithApproachingDeadlines();

        // Set attributes for the dashboard view
        request.setAttribute("totalProjects", totalProjects);
        request.setAttribute("recentProjects", recentProjects);
        request.setAttribute("totalTasks", totalTasks);
        request.setAttribute("taskStatusCounts", taskStatusCounts);
        request.setAttribute("completionPercentage", completionPercentage);
        request.setAttribute("totalResources", totalResources);
        request.setAttribute("resourceTypeCounts", resourceTypeCounts);
        request.setAttribute("urgentTasks", urgentTasks);
        request.setAttribute("projectsWithApproachingDeadlines", projectsWithApproachingDeadlines);

        // Forward to the dashboard view
        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Dashboard currently doesn't handle POST requests
        // If needed in the future, you could add functionality here
        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}