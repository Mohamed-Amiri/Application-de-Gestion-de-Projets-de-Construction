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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier Projet - ConstructionXpert</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #023047;
            --secondary-color: #ffb703;
            --accent-color: #fb8500;
            --light-color: #f8f9fa;
        }

        body {
            background-color: var(--light-color);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .edit-header {
            background: linear-gradient(rgba(2, 48, 71, 0.9), rgba(2, 48, 71, 0.9)),
            url('/api/placeholder/1920/600');
            color: white;
            padding: 4rem 0;
            margin-bottom: 2rem;
        }

        .edit-container {
            max-width: 800px;
            margin: 2rem auto;
            padding: 0 15px;
        }

        .edit-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 2rem;
        }

        .form-label {
            font-weight: 600;
            color: var(--primary-color);
            margin-bottom: 0.5rem;
        }

        .form-control:focus {
            border-color: var(--secondary-color);
            box-shadow: 0 0 0 0.25rem rgba(255, 183, 3, 0.25);
        }

        .btn-primary {
            background-color: var(--secondary-color);
            border-color: var(--secondary-color);
            color: var(--primary-color);
            padding: 0.5rem 2rem;
            font-weight: 600;
        }

        .btn-primary:hover {
            background-color: var(--accent-color);
            border-color: var(--accent-color);
            color: white;
        }

        .btn-cancel {
            border: 2px solid var(--primary-color);
            color: var(--primary-color);
        }

        .btn-cancel:hover {
            background-color: var(--primary-color);
            color: white;
        }
    </style>
</head>
<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="#">
            <i class="fas fa-building"></i> ConstructionXpert
        </a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="Project.jsp">
                        <i class="fas fa-arrow-left"></i> Retour
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- En-tête -->
<div class="edit-header text-center">
    <div class="container">
        <h1 class="display-5 mb-3">
            <i class="fas fa-edit"></i> Modification de Projet
        </h1>
        <p class="lead"><%= projet.getNom() %></p>
    </div>
</div>

<!-- Contenu principal -->
<div class="edit-container">
    <div class="edit-card">
        <form action="ProjetServlet" method="post">
            <input type="hidden" name="action" value="modifier">
            <input type="hidden" name="id" value="<%= projet.getId() %>">

            <div class="row g-4">
                <!-- Colonne Gauche -->
                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Nom du projet</label>
                        <input type="text" name="nom" class="form-control"
                               value="<%= projet.getNom() %>" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Date de début</label>
                        <input type="date" name="dateDebut" class="form-control"
                               value="<%= projet.getDateDebut() != null ?
                               new java.text.SimpleDateFormat("yyyy-MM-dd").format(projet.getDateDebut()) : "" %>"
                               required>
                    </div>
                </div>

                <!-- Colonne Droite -->
                <div class="col-md-6">
                    <div class="mb-3">
                        <label class="form-label">Budget</label>
                        <div class="input-group">
                            <input type="number" name="budget" class="form-control"
                                   value="<%= projet.getBudget() %>" step="0.01" required>
                            <span class="input-group-text">€</span>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Date de fin</label>
                        <input type="date" name="dateFin" class="form-control"
                               value="<%= projet.getDateFin() != null ?
                               new java.text.SimpleDateFormat("yyyy-MM-dd").format(projet.getDateFin()) : "" %>"
                               required>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Description</label>
                <textarea name="description" class="form-control"
                          rows="4" required><%= projet.getDescription() %></textarea>
            </div>

            <div class="d-flex justify-content-end gap-3 mt-4">
                <a href="Project.jsp" class="btn btn-cancel">Annuler</a>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save me-2"></i>Enregistrer
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-light py-4 mt-5">
    <div class="container text-center">
        <p class="mb-0">ConstructionXpert Services © 2025</p>
        <div class="mt-2">
            <a href="#" class="text-light mx-2"><i class="fab fa-linkedin"></i></a>
            <a href="#" class="text-light mx-2"><i class="fab fa-twitter"></i></a>
            <a href="#" class="text-light mx-2"><i class="fas fa-envelope"></i></a>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>