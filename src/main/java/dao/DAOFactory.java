package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DAOFactory {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    static {
        try {
            // Assume the persistence unit name is "project-management"
            entityManagerFactory = Persistence.createEntityManagerFactory("project-management");
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            // Log the exception
            System.err.println("Error initializing EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    public static ProjetDAO getProjetDAO() {
        return new ProjetDAO(entityManager);
    }

    public static TacheDAO getTacheDAO() {
        return new TacheDAO(entityManager);
    }

    public static RessourceDAO getRessourceDAO() {
        return new RessourceDAO(entityManager);
    }

    public static AffectationRessourceDAO getAffectationRessourceDAO() {
        return new AffectationRessourceDAO(entityManager);
    }

    public static void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
