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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/TacheServlet")
public class TacheServlet extends HttpServlet {
    private TacheDAO tacheDAO = new TacheDAO();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String redirectUrl = "TacheServlet?action=lister";

        try {
            switch(action != null ? action : "") {
                case "ajouter":
                    ajouterTache(request);
                    break;
                case "modifier":
                    modifierTache(request);
                    break;
                case "supprimer":
                    supprimerTache(request);
                    break;
                default:
                    request.getSession().setAttribute("erreur", "Action non reconnue");
            }
        } catch (Exception e) {
            gererErreur(request, e);
            redirectUrl = "erreur.jsp";
        }

        response.sendRedirect(redirectUrl);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            switch(action != null ? action : "") {
                case "editer":
                    afficherFormulaireEdition(request, response);
                    break;
                case "lister":
                    listerTaches(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
            }
        } catch (Exception e) {
            gererErreur(request, e);
            request.getRequestDispatcher("erreur.jsp").forward(request, response);
        }
    }

    private void ajouterTache(HttpServletRequest request)
            throws ParseException, SQLException, NumberFormatException {

        Tache tache = creerTacheDepuisRequest(request);
        tacheDAO.ajouterTache(tache);
        request.getSession().setAttribute("succes", "Tâche ajoutée avec succès");
    }

    private void modifierTache(HttpServletRequest request)
            throws ParseException, SQLException, NumberFormatException {

        Tache tache = creerTacheDepuisRequest(request);
        tache.setId(Integer.parseInt(request.getParameter("id")));
        tacheDAO.modifierTache(tache);
        request.getSession().setAttribute("succes", "Tâche modifiée avec succès");
    }

    private void supprimerTache(HttpServletRequest request)
            throws SQLException, NumberFormatException {

        int id = Integer.parseInt(request.getParameter("id"));
        tacheDAO.supprimerTache(id);
        request.getSession().setAttribute("succes", "Tâche supprimée avec succès");
    }

    private Tache creerTacheDepuisRequest(HttpServletRequest request) throws ParseException {
        Date dateDebut = DATE_FORMAT.parse(request.getParameter("dateDebut"));
        Date dateFin = DATE_FORMAT.parse(request.getParameter("dateFin"));

        return new Tache(
                0,
                Integer.parseInt(request.getParameter("idProjet")),
                request.getParameter("nom"),
                request.getParameter("description"),
                dateDebut,
                dateFin,
                request.getParameter("etat")
        );
    }

    private void afficherFormulaireEdition(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException, NumberFormatException {

        int id = Integer.parseInt(request.getParameter("id"));
        Tache tache = tacheDAO.getTacheParId(id);
        request.setAttribute("tache", tache);
        request.getRequestDispatcher("editerTache.jsp").forward(request, response);
    }

    private void listerTaches(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<Tache> taches = tacheDAO.getToutesLesTaches();
        request.setAttribute("taches", taches);
        request.getRequestDispatcher("taches.jsp").forward(request, response);
    }

    private void gererErreur(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        request.getSession().setAttribute("erreur",
                "Erreur: " + e.getMessage() + ". Consultez les logs pour plus de détails.");
    }
}