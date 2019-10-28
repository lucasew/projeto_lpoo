package controller;

import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseController {
    private static EntityManagerFactory factory = null;
    private static EntityManager manager = null;

    public static EntityManager getInstance() {
        if (manager == null) {
            synchronized (DatabaseController.class) {
                if (manager == null) {
                    try {
                        factory = Persistence.createEntityManagerFactory("database");
                        manager = factory.createEntityManager();
                    } catch (HibernateException he) {
                        System.err.println(he.getMessage());
                    }
                }
            }
        }
        return manager;
    }

    public static void close() {
        manager.close();
        factory.close();
    }
}
