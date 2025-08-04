package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceConfiguration;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final EntityManagerFactory emf = new PersistenceConfiguration("Users")
                .managedClass(User.class)
                .managedClass(Toy.class)
                .createEntityManagerFactory();

        App app = new App(emf);

        app.run();
    }

    private final EntityManagerFactory emf;

    public App(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void run() {
        populateDb();
        emf.close();
    }

    private void populateDb() {
        // An hibernate Session is an EntityManager and is a *Stateful* session. It mediates the interaction
        // between our program and the database via operations on a *persistence context*.
        // A persistence context is a sort of cache: it contains a unique mapping from the identifier of an
        // entity to the entity instance itself.
        // The lifetime of a persistence context usually corresponds to the lifetime of a transacton.
        // WARNING: A persistence context, that is a Session or EntityManager **absolutely positively
        // must not be shared between multiple threads or between concurrent transactions**.
        final EntityManager em = emf.createEntityManager();

        // Idiom recommended by Hibernate guide section 5.3:
        // See https://docs.jboss.org/hibernate/orm/7.0/introduction/html_single/Hibernate_Introduction.html#persistence-contexts
        // Can sometime be simplified as:
        // emf.runInTransaction(entityManager -> {
        //     // do some work
        //     ...
        // });
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            User john = new User("John Doe", "john.doe@gmail.com");
            john.getFavoriteToys().addAll(List.of(
                new Toy("Buzz Lightning", 5, john),
                new Toy("GameBoy", 5, john)
            ));
            em.persist(john);  // No actual insertion here. Merely schedule an insertion into the db.

            tx.commit();

            System.out.printf("Save User %s\n", john);
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

}
