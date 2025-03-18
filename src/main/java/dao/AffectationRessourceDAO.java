package dao;

import model.AffectationRessource;
import model.Ressource;
import model.Tache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AffectationRessourceDAO {

    private final EntityManager entityManager;

    public AffectationRessourceDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create
    public void save(AffectationRessource affectation) {
        entityManager.getTransaction().begin();
        entityManager.persist(affectation);
        entityManager.getTransaction().commit();
    }

    // Read
    public AffectationRessource findById(Integer id) {
        return entityManager.find(AffectationRessource.class, id);
    }

    public List<AffectationRessource> findAll() {
        TypedQuery<AffectationRessource> query = entityManager.createQuery(
                "SELECT a FROM AffectationRessource a", AffectationRessource.class);
        return query.getResultList();
    }

    // Find assignments by task
    public List<AffectationRessource> findByTache(Tache tache) {
        TypedQuery<AffectationRessource> query = entityManager.createQuery("SELECT a FROM AffectationRessource a WHERE a.tache = :tache", AffectationRessource.class);
        query.setParameter("tache", tache);
        return query.getResultList();
    }

    // Find assignments by resource
    public List<AffectationRessource> findByRessource(Ressource ressource) {
        TypedQuery<AffectationRessource> query = entityManager.createQuery(
                "SELECT a FROM AffectationRessource a WHERE a.ressource = :ressource", AffectationRessource.class);
        query.setParameter("ressource", ressource);
        return query.getResultList();
    }

    // Find assignments with quantity greater than specified amount
    public List<AffectationRessource> findByQuantiteUtiliseeGreaterThan(Integer quantite) {
        TypedQuery<AffectationRessource> query = entityManager.createQuery(
                "SELECT a FROM AffectationRessource a WHERE a.quantite_utilisee > :quantite",
                AffectationRessource.class);
        query.setParameter("quantite", quantite);
        return query.getResultList();
    }

    // Update
    public void update(AffectationRessource affectation) {
        entityManager.getTransaction().begin();
        entityManager.merge(affectation);
        entityManager.getTransaction().commit();
    }

    // Delete
    public void delete(AffectationRessource affectation) {
        entityManager.getTransaction().begin();
        entityManager.remove(affectation);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        AffectationRessource affectation = findById(id);
        if (affectation != null) {
            delete(affectation);
        }
    }

    // Delete all assignments for a task
    public void deleteByTache(Tache tache) {
        List<AffectationRessource> affectations = findByTache(tache);
        entityManager.getTransaction().begin();
        for (AffectationRessource affectation : affectations) {
            entityManager.remove(affectation);
        }
        entityManager.getTransaction().commit();
    }

    // Calculate total resource usage across all tasks
    public Integer calculerUtilisationTotaleRessource(Ressource ressource) {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT SUM(a.quantite_utilisee) FROM AffectationRessource a WHERE a.ressource = :ressource",
                Integer.class);
        query.setParameter("ressource", ressource);
        Integer result = query.getSingleResult();
        return result != null ? result : 0;
    }
}
