package servlet;
import dao.RessourceDAO;
import model.Ressource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/RessourceServlet")
public class RessourceServlet extends HttpServlet {
    private RessourceDAO ressourceDAO = new RessourceDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("ajouter".equals(action)) {
            try {
                String nom = request.getParameter("nom");
                String type = request.getParameter("type");
                int quantite = Integer.parseInt(request.getParameter("quantite"));
                String fournisseur = request.getParameter("fournisseur");
                double coutUnitaire = Double.parseDouble(request.getParameter("coutUnitaire"));
                Ressource ressource = new Ressource(0, nom, type, quantite, fournisseur, coutUnitaire);
                ressourceDAO.ajouterRessource(ressource);
                response.sendRedirect("RessourceServlet?action=lister");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("lister".equals(action)) {
            try {
                List<Ressource> ressources = ressourceDAO.getToutesLesRessources();
                request.setAttribute("ressources", ressources);
                request.getRequestDispatcher("ressources.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }
}
