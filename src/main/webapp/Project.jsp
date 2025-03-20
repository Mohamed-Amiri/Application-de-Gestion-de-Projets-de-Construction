<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Projet" %>
<%@ page import="dao.ProjetDAO" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Projets</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body { padding: 20px; }
        .container { margin-top: 20px; }
    </style>
</head>
<body>

<div class="container">
    <h1>Gestion des Projets</h1>

    <!-- Ajout d'un Projet -->
    <h2>Ajouter un Projet</h2>
    <form action="ProjetServlet" method="Post">
        <input type="hidden" name="action" value="ajouter">
        <div class="form-group">
            <label for="nom">Nom:</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea class="form-control" id="description" name="description" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="dateDebut">Date de Début:</label>
            <input type="date" class="form-control" id="dateDebut" name="dateDebut" required>
        </div>
        <div class="form-group">
            <label for="dateFin">Date de Fin:</label>
            <input type="date" class="form-control" id="dateFin" name="dateFin" required>
        </div>
        <div class="form-group">
            <label for="budget">Budget:</label>
            <input type="number" class="form-control" id="budget" name="budget" step="0.01" required>
        </div>
        <button type="submit" class="btn btn-primary">Ajouter</button>
    </form>

    <hr>

    <!-- Liste des Projets -->
    <h2>Liste des Projets</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Date de Début</th>
            <th>Date de Fin</th>
            <th>Budget</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            ProjetDAO projetDAO = new ProjetDAO();
            List<Projet> projets = projetDAO.getTousLesProjets();
            if (projets != null && !projets.isEmpty()) {
                for (Projet projet : projets) {
        %>
        <tr>
            <td><%= projet.getId() %></td>
            <td><%= projet.getNom() %></td>
            <td><%= projet.getDescription() %></td>
            <td><%= projet.getDateDebut() %></td>
            <td><%= projet.getDateFin() %></td>
            <td><%= projet.getBudget() %></td>
            <td>
                <!-- Formulaire pour modifier un projet -->
                <form action="ProjetServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="modifier">
                    <input type="hidden" name="id" value="<%= projet.getId() %>">
                    <button type="submit" class="btn btn-warning btn-sm">Modifier</button>
                </form>
                <!-- Formulaire pour supprimer un projet -->
                <form action="ProjetServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="supprimer">
                    <input type="hidden" name="id" value="<%= projet.getId() %>">
                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce projet ?');">Supprimer</button>
                </form>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="7">Aucun projet trouvé.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
