package org.example;

import jakarta.persistence.*;

/**
 * An entity must:
 * - be a non-final class,
 * - with a non-private no-arg constructor
 */
@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public Long getId() { return this.id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("User(id = %d, name = %s, email = %s)", id, name, email);
    }
}
