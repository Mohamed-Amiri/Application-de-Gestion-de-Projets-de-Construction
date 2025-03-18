package dao;

import model.Projet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProjetDAO {

    private EntityManager entityManager;

    public ProjetDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create
    public void save(Projet projet) {
        entityManager.getTransaction().begin();
        entityManager.persist(projet);
        entityManager.getTransaction().commit();
    }

    // Read
    public Projet findById(Integer id) {
        return entityManager.find(Projet.class, id);
    }

    public List<Projet> findAll() {
        TypedQuery<Projet> query = entityManager.createQuery("SELECT p FROM Projet p", Projet.class);
        return query.getResultList();
    }

    // Find projects by name (partial match)
    public List<Projet> findByName(String name) {
        TypedQuery<Projet> query = entityManager.createQuery(
                "SELECT p FROM Projet p WHERE p.nom LIKE :name", Projet.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    // Find projects with budget greater than specified amount
    public List<Projet> findByBudgetGreaterThan(double budget) {
        TypedQuery<Projet> query = entityManager.createQuery(
                "SELECT p FROM Projet p WHERE p.budget > :budget", Projet.class);
        query.setParameter("budget", budget);
        return query.getResultList();
    }

    // Update
    public void update(Projet projet) {
        entityManager.getTransaction().begin();
        entityManager.merge(projet);
        entityManager.getTransaction().commit();
    }

    // Delete
    public void delete(Projet projet) {
        entityManager.getTransaction().begin();
        entityManager.remove(projet);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Projet projet = findById(id);
        if (projet != null) {
            delete(projet);
        }
    }

    // Count all projects
    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Projet p", Long.class);
        return query.getSingleResult();
    }
}