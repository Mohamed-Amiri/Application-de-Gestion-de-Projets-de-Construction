package servlet;

import dao.TacheDAO;
import model.Tache;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/TacheServlet")
public class TacheServlet extends HttpServlet {
    private TacheDAO tacheDAO = new TacheDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("ajouter".equals(action)) {
            try {
                int idProjet = Integer.parseInt(request.getParameter("idProjet"));
                String nom = request.getParameter("nom");
                String description = request.getParameter("description");
                String etat = request.getParameter("etat");
                Tache tache = new Tache(0, idProjet, nom, description, null, null, etat);
                tacheDAO.ajouterTache(tache);
                response.sendRedirect("TacheServlet?action=lister");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("lister".equals(action)) {
            try {
                List<Tache> taches = tacheDAO.getToutesLesTaches();
                request.setAttribute("taches", taches);
                request.getRequestDispatcher("taches.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }
}
