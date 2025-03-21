<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.SQLException, java.util.List, model.Projet, dao.ProjetDAO" %>
<%@ page import="dao.ConnectToDB" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConstructionXpert - Gestion de Projets</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Custom CSS -->
    <style>
        :root {
            --primary-color: #023047;
            --secondary-color: #ffb703;
            --accent-color: #fb8500;
            --light-color: #f8f9fa;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--light-color);
        }

        .navbar {
            background-color: var(--primary-color) !important;
        }

        .btn-primary {
            background-color: var(--secondary-color);
            border-color: var(--secondary-color);
            color: var(--primary-color);
        }

        .table thead {
            background-color: var(--primary-color);
            color: white;
        }

        .form-section {
            background: white;
            border-radius: 8px;
            padding: 2rem;
            margin: 2rem 0;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
        }

        .management-header {
            background: linear-gradient(rgba(2, 48, 71, 0.9), rgba(2, 48, 71, 0.9)),
            url('/api/placeholder/1920/600');
            color: white;
            padding: 4rem 0;
            margin-bottom: 2rem;
        }
    </style>
</head>
<body>

<!-- Database Connection -->
<%
    Connection con = null;
    try {
        ConnectToDB dbConnector = new ConnectToDB();
        con = dbConnector.getConnection();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
%>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand" href="#">ConstructionXpert</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-home"></i> Accueil</a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="projectsDropdown" role="button" data-bs-toggle="dropdown">
                        <i class="fas fa-building"></i> Gestion
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#projects">Projets</a></li>
                        <li><a class="dropdown-item" href="#tasks">Tâches</a></li>
                        <li><a class="dropdown-item" href="#resources">Ressources</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Management Header -->
<header class="management-header text-center">
    <div class="container">
        <h1 class="display-4 mb-4"><i class="fas fa-tools"></i> Gestion des Projets</h1>
        <p class="lead">Plateforme de gestion intégrée pour vos chantiers de construction</p>
    </div>
</header>

<main class="container">
    <!-- Project Creation Section -->
    <section class="form-section" id="projects">
        <h2 class="mb-4"><i class="fas fa-plus-circle"></i> Créer un Nouveau Projet</h2>
        <form action="ProjetServlet" method="POST">
            <input type="hidden" name="action" value="ajouter">
            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Nom du Projet</label>
                    <input type="text" name="nom" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Budget</label>
                    <input type="number" name="budget" class="form-control" step="0.01" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Date de Début</label>
                    <input type="date" name="dateDebut" class="form-control" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Date de Fin</label>
                    <input type="date" name="dateFin" class="form-control" required>
                </div>
                <div class="col-12">
                    <label class="form-label">Description</label>
                    <textarea name="description" class="form-control" rows="3"></textarea>
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-primary px-4"><i class="fas fa-save"></i> Enregistrer</button>
                </div>
            </div>
        </form>
    </section>

    <!-- Projects List -->
    <section class="mt-5">
        <h2 class="mb-4"><i class="fas fa-list-ul"></i> Projets Actifs</h2>
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Dates</th>
                    <th>Budget</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    ProjetDAO dao = new ProjetDAO();
                    List<Projet> projets = dao.getTousLesProjets();

                    if(projets != null) {
                        for(Projet p : projets) {
                %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td>
                        <h5 class="mb-1"><%= p.getNom() %></h5>
                        <p class="text-muted mb-0"><%= p.getDescription() %></p>
                    </td>
                    <td>
                        <%= p.getDateDebut() %> <br>
                        <strong>au</strong> <%= p.getDateFin() %>
                    </td>
                    <td><%= String.format("%,.2f €", p.getBudget()) %></td>
                    <td>
                        <div class="d-flex gap-2">
                            <a href="ProjetServlet?action=modifier&id=<%= p.getId() %>"
                               class="btn btn-sm btn-outline-secondary">
                                <i class="fas fa-edit"></i>
                            </a>
                            <form action="ProjetServlet" method="POST" class="d-inline">
                                <input type="hidden" name="action" value="supprimer">
                                <input type="hidden" name="id" value="<%= p.getId() %>">
                                <button type="submit" class="btn btn-sm btn-danger"
                                        onclick="return confirm('Supprimer ce projet définitivement ?')">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
                <% }
                } else { %>
                <tr>
                    <td colspan="5" class="text-center text-muted py-4">
                        Aucun projet enregistré
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</main>

<!-- Footer -->
<footer class="bg-dark text-light py-4 mt-5">
    <div class="container text-center">
        <p class="mb-0">ConstructionXpert Services © 2025 - Tous droits réservés</p>
        <div class="mt-2">
            <a href="#" class="text-light mx-2"><i class="fab fa-linkedin"></i></a>
            <a href="#" class="text-light mx-2"><i class="fab fa-twitter"></i></a>
            <a href="#" class="text-light mx-2"><i class="fas fa-envelope"></i></a>
        </div>
    </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Activation des popovers
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
    const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))

    // Gestion des confirmations de suppression
    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            if(!confirm('Êtes-vous sûr de vouloir supprimer cet élément?')) {
                e.preventDefault()
            }
        })
    })
</script>
</body>
</html>