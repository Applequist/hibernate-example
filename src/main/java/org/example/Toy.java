package org.example;

import jakarta.persistence.*;

@Entity(name = "TOYS")
public class Toy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private int minAge;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    public Toy() {}

    public Toy(String name, int minAge,  User owner) {
        this.name = name;
        this.minAge = minAge;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
