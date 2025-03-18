package dao;

import model.Ressource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class RessourceDAO {

    private EntityManager entityManager;

    public RessourceDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create
    public void save(Ressource ressource) {
        entityManager.getTransaction().begin();
        entityManager.persist(ressource);
        entityManager.getTransaction().commit();
    }

    // Read
    public Ressource findById(Integer id) {
        return entityManager.find(Ressource.class, id);
    }

    public List<Ressource> findAll() {
        TypedQuery<Ressource> query = entityManager.createQuery("SELECT r FROM Ressource r", Ressource.class);
        return query.getResultList();
    }

    // Find resources by type
    public List<Ressource> findByType(Ressource.TypeRessource type) {
        TypedQuery<Ressource> query = entityManager.createQuery(
                "SELECT r FROM Ressource r WHERE r.type = :type", Ressource.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    // Find resources by supplier
    public List<Ressource> findByFournisseur(String fournisseur) {
        TypedQuery<Ressource> query = entityManager.createQuery(
                "SELECT r FROM Ressource r WHERE r.fournisseur = :fournisseur", Ressource.class);
        query.setParameter("fournisseur", fournisseur);
        return query.getResultList();
    }

    // Find resources with unit cost less than specified amount
    public List<Ressource> findByCoutUnitaireLessThan(BigDecimal cout) {
        TypedQuery<Ressource> query = entityManager.createQuery(
                "SELECT r FROM Ressource r WHERE r.cout_unitaire < :cout", Ressource.class);
        query.setParameter("cout", cout);
        return query.getResultList();
    }

    // Find resources with available quantity greater than specified amount
    public List<Ressource> findByQuantiteGreaterThan(Integer quantite) {
        TypedQuery<Ressource> query = entityManager.createQuery(
                "SELECT r FROM Ressource r WHERE r.quantite > :quantite", Ressource.class);
        query.setParameter("quantite", quantite);
        return query.getResultList();
    }

    // Update
    public void update(Ressource ressource) {
        entityManager.getTransaction().begin();
        entityManager.merge(ressource);
        entityManager.getTransaction().commit();
    }

    // Delete
    public void delete(Ressource ressource) {
        entityManager.getTransaction().begin();
        entityManager.remove(ressource);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Ressource ressource = findById(id);
        if (ressource != null) {
            delete(ressource);
        }
    }

    // Count resources by type
    public long countByType(Ressource.TypeRessource type) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(r) FROM Ressource r WHERE r.type = :type", Long.class);
        query.setParameter("type", type);
        return query.getSingleResult();
    }
}