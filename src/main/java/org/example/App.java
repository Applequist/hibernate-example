package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final EntityManagerFactory emf = new PersistenceConfiguration("Users")
                .managedClass(User.class)
                .createEntityManagerFactory();

        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        User john = new User("John Doe", "john.doe@gmail.com");
        em.persist(john);
        em.getTransaction().commit();

        System.out.printf("Save User %s\n", john);

        em.close();

        emf.close();
    }
}
