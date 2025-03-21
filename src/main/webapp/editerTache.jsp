<%-- editerTache.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="tache" scope="request" type="model.Tache"/>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Édition Tâche - ConstructionXpert</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .form-section {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
            padding: 2rem;
        }
    </style>
</head>
<body>
<%@ include file="navbar.jsp" %>

<main class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>
                    <i class="fas fa-edit me-2"></i>
                    ${empty tache.id ? 'Nouvelle Tâche' : 'Modifier Tâche'}
                </h2>
                <a href="TacheServlet?action=lister" class="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-2"></i>Retour
                </a>
            </div>

            <c:if test="${not empty erreur}">
                <div class="alert alert-danger">${erreur}</div>
            </c:if>

            <div class="form-section">
                <form action="TacheServlet" method="post">
                    <input type="hidden" name="action" value="${empty tache.id ? 'ajouter' : 'modifier'}">
                    <input type="hidden" name="id" value="${tache.id}">

                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label">Nom de la tâche</label>
                                <input type="text" name="nom" class="form-control"
                                       value="${tache.nom}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Projet associé</label>
                                <select name="idProjet" class="form-select" required>
                                    <!-- À remplacer par une liste dynamique des projets -->
                                    <option value="1" ${tache.idProjet == 1 ? 'selected' : ''}>Projet 1</option>
                                    <option value="2" ${tache.idProjet == 2 ? 'selected' : ''}>Projet 2</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="mb-3">
                                <label class="form-label">État</label>
                                <select name="etat" class="form-select" required>
                                    <option value="En attente" ${tache.etat == 'En attente' ? 'selected' : ''}>En attente</option>
                                    <option value="En cours" ${tache.etat == 'En cours' ? 'selected' : ''}>En cours</option>
                                    <option value="Terminée" ${tache.etat == 'Terminée' ? 'selected' : ''}>Terminée</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Dates</label>
                                <div class="row g-2">
                                    <div class="col-6">
                                        <input type="date" name="dateDebut"
                                               value="${tache.dateDebut}"
                                               class="form-control" required>
                                    </div>
                                    <div class="col-6">
                                        <input type="date" name="dateFin"
                                               value="${tache.dateFin}"
                                               class="form-control" required>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-12">
                            <div class="mb-3">
                                <label class="form-label">Description</label>
                                <textarea name="description" class="form-control"
                                          rows="4">${tache.description}</textarea>
                            </div>
                        </div>

                        <div class="col-12 text-end">
                            <button type="submit" class="btn btn-primary px-4">
                                <i class="fas fa-save me-2"></i>Enregistrer
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<%@ include file="footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>