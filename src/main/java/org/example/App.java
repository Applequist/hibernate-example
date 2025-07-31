package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        // Hold services needed at bootstrap and run time:
        // - ClassLoaderService
        // - IntegratorService
        // - StrategySelector
        final BootstrapServiceRegistry bootstrapRegistry = new BootstrapServiceRegistryBuilder().build();

        Configuration cfg = new Configuration(bootstrapRegistry)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", "jdbc:sqlite:/home/bdesoutter/dev/repositories/hibernate/identifier.sqlite")
                .setProperty("hibernate.connection.pool_size", "5")
                .setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .addAnnotatedClass(User.class);

        // A thread-safe and immutable representation of the mapping of the application
        // domain model to a database.
        // A SessionFactory is very expensive to create, so, for any given database,
        // the application should have only one associated SessionFactory.
        // JPA equivalant: EntityManagerFactory
        final SessionFactory sessionFactory = cfg.buildSessionFactory();

        // A **single threaded**, short-lived object conceptually modeling a "Unit of Work".
        // Wraps a SQL connection and acts as a factory for Hibernate Transaction.
        // Also maintain a "repeatable read" persistence context (first level cache)
        // JPA equivalent: EntityManager
        Session session = sessionFactory.openSession();

        // A **single-threaded**, short-lived object used by the application to demarcate
        // individual physical transaction boundaries.
        final Transaction transaction = session.beginTransaction();

        User john = new User();
        john.setName("John Doe");
        john.setEmail("john.doe@gmail.com");
        session.persist(john);

        transaction.commit();

        System.out.printf("Save User %s\n", john);
        session.close();
        sessionFactory.close();
    }
}
