package com.sqli.hibernate7.entity;

import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.EmbeddedColumnNaming;

/**
 * Avec Hibernate < 7 :
 * https://docs.jboss.org/hibernate/orm/7.0/userguide/html_single/Hibernate_User_Guide.html#embeddable-override
 * Impossible d'avoir plusieurs Embedded du même type sans solution de contournement fastidieuse, à base d'{@link AssociationOverrides}
 */
@Entity
@Table(name = "Persons")
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    @Embedded
    @EmbeddedColumnNaming("home_%s")
    private Address homeAddress;

    @Embedded
    @EmbeddedColumnNaming("work_%s")
    private Address workAddress;

    public Person() {
    }

    public Person(String firstName, String lastName, Address homeAddress, Address workAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.homeAddress = homeAddress;
        this.workAddress = workAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + (Hibernate.isInitialized(getFirstName()) ? getFirstName() : "NOT INITIALIZED") + '\'' +
                ", lastName='" + (Hibernate.isInitialized(getLastName()) ? getLastName() : "NOT INITIALIZED") + '\'' +
                ", homeAddress=" + (Hibernate.isInitialized(getHomeAddress()) ? getHomeAddress() : "NOT INITIALIZED") +
                ", workAddress=" + (Hibernate.isInitialized(getWorkAddress()) ? getWorkAddress() : "NOT INITIALIZED") +
                '}';
    }
}
