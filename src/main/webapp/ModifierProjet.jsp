<%@ page import="model.Projet, dao.ProjetDAO, java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    ProjetDAO projetDAO = new ProjetDAO();
    Projet projet = projetDAO.getProjetParId(id);
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier Projet</title>
</head>
<body>
<h2>Modifier Projet</h2>
<form action="ProjetServlet" method="post">
    <input type="hidden" name="action" value="modifier">
    <input type="hidden" name="id" value="<%= projet.getId() %>">

    <label>Nom :</label>
    <input type="text" name="nom" value="<%= projet.getNom() %>" required><br>

    <label>Description :</label>
    <textarea name="description" required><%= projet.getDescription() %></textarea><br>

    <label>Budget :</label>
    <input type="number" name="budget" step="0.01" value="<%= projet.getBudget() %>" required><br>

    <label>Date Début :</label>
    <input type="date" name="dateDebut" value="<%= projet.getDateDebut() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(projet.getDateDebut()) : "" %>"><br>

    <label>Date Fin :</label>
    <input type="date" name="dateFin" value="<%= projet.getDateFin() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(projet.getDateFin()) : "" %>"><br>

    <button type="submit">Modifier</button>
    <a href="Project.jsp">Annuler</a>
</form>
</body>
</html>
