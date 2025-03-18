package dao;

import model.Tache;
import model.Projet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class TacheDAO {

    private EntityManager entityManager;

    public TacheDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create
    public void save(Tache tache) {
        entityManager.getTransaction().begin();
        entityManager.persist(tache);
        entityManager.getTransaction().commit();
    }

    // Read
    public Tache findById(Integer id) {
        return entityManager.find(Tache.class, id);
    }

    public List<Tache> findAll() {
        TypedQuery<Tache> query = entityManager.createQuery("SELECT t FROM Tache t", Tache.class);
        return query.getResultList();
    }

    // Find tasks by project
    public List<Tache> findByProjet(Projet projet) {
        TypedQuery<Tache> query = entityManager.createQuery(
                "SELECT t FROM Tache t WHERE t.projet = :projet", Tache.class);
        query.setParameter("projet", projet);
        return query.getResultList();
    }

    // Find tasks by state
    public Long findByEtat(Tache.EtatTache etat) {
        TypedQuery<Tache> query = entityManager.createQuery(
                "SELECT t FROM Tache t WHERE t.etat = :etat", Tache.class);
        query.setParameter("etat", etat);
        return query.getResultList();
    }

    // Find tasks by date range
    public List<Tache> findByDateRange(Date startDate, Date endDate) {
        TypedQuery<Tache> query = entityManager.createQuery(
                "SELECT t FROM Tache t WHERE t.date_debut >= :startDate AND t.date_fin <= :endDate",
                Tache.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    // Update
    public void update(Tache tache) {
        entityManager.getTransaction().begin();
        entityManager.merge(tache);
        entityManager.getTransaction().commit();
    }

    // Delete
    public void delete(Tache tache) {
        entityManager.getTransaction().begin();
        entityManager.remove(tache);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Tache tache = findById(id);
        if (tache != null) {
            delete(tache);
        }
    }

    // Count tasks by project
    public long countByProjet(Projet projet) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(t) FROM Tache t WHERE t.projet = :projet", Long.class);
        query.setParameter("projet", projet);
        return query.getSingleResult();
    }
}