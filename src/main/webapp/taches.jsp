<%@ page import="dao.TacheDAO" %>
<%@ page import="model.Tache" %>
<%@ page import="java.util.List" %><%-- taches.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Tâches - ConstructionXpert</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #023047;
            --secondary-color: #ffb703;
            --accent-color: #fb8500;
            --light-color: #f8f9fa;
        }

        .status-badge {
            padding: 0.4em 0.8em;
            border-radius: 20px;
            font-size: 0.9em;
        }

        .status-en-attente { background-color: #ffd700; color: #000; }
        .status-en-cours { background-color: #4CAF50; color: white; }
        .status-terminee { background-color: #607D8B; color: white; }

        .table-hover tbody tr:hover {
            background-color: rgba(2, 48, 71, 0.05);
        }
    </style>
</head>
<body>
<%@ include file="navbar.jsp" %>

<main class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="fas fa-tasks me-2"></i>Gestion des Tâches</h2>
        <a href="TacheServlet?action=editer" class="btn btn-primary">
            <i class="fas fa-plus me-2"></i>Nouvelle Tâche
        </a>
    </div>

    <c:if test="${not empty succes}">
        <div class="alert alert-success">${succes}</div>
    </c:if>

    <div class="card shadow">
        <div class="card-body">
            <table class="table table-hover">
                <thead class="thead-dark">
                <tr>
                    <th>Nom</th>
                    <th>Projet</th>
                    <th>Dates</th>
                    <th>État</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    TacheDAO dao = new TacheDAO();
                    List<Tache> task = dao.getToutesLesTaches();

                    if(task != null) {
                        for(Tache t : task) {
                %>


                <c:forEach items="${t.id}" var="tache">
                    <tr>
                        <td>${tache.nom}</td>
                        <td>Projet #${tache.idProjet}</td>
                        <td>
                            <div>${tache.dateDebut}</div>
                            <div class="text-muted small">au ${tache.dateFin}</div>
                        </td>
                        <td>
                                    <span class="status-badge status-${tache.etat.toLowerCase().replace(' ', '-')}">
                                            ${tache.etat}
                                    </span>
                        </td>
                        <td>
                            <a href="TacheServlet?action=editer&id=${tache.id}"
                               class="btn btn-sm btn-outline-secondary me-2">
                                <i class="fas fa-edit"></i>
                            </a>
                            <form action="TacheServlet" method="post" class="d-inline">
                                <input type="hidden" name="action" value="supprimer">
                                <input type="hidden" name="id" value="${tache.id}">
                                <button type="submit" class="btn btn-sm btn-danger"
                                        onclick="return confirm('Supprimer cette tâche ?')">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>