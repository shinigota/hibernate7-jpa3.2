package com.sqli.hibernate7.entity;

import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NamedEntityGraph;

import java.util.Collection;

@Entity
@Table(name = "Shops")
@org.hibernate.annotations.NamedEntityGraph(
        name = "Shop.withEmployees",
        graph = "employees"
)
@org.hibernate.annotations.NamedEntityGraph(
        name = "Shop.withBooksAndTheirAuthor",
        graph = "books(author)"
)
@jakarta.persistence.NamedEntityGraph(
        name = "Shop.withEmployees.nativeJpa",
        attributeNodes = @NamedAttributeNode("employees")
)
@NamedQuery(name = "Shop.findAllByOwnerId", query = "SELECT s FROM Shop s WHERE s.owner.id = :id")
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person owner;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Person> employees;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Book> books;

    public Shop() {
    }

    public Shop(Address address, Person owner, Collection<Person> employees, Collection<Book> books) {
        this.address = address;
        this.owner = owner;
        this.employees = employees;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Collection<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<Person> employees) {
        this.employees = employees;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", address=" + (Hibernate.isInitialized(getAddress()) ? getAddress() : "NOT INITIALIZED") +
                ", owner=" + (Hibernate.isInitialized(getOwner()) ? getOwner() : "NOT INITIALIZED") +
                ", employees=" + (Hibernate.isInitialized(getEmployees()) ? getEmployees() : "NOT INITIALIZED") +
                ", books=" + (Hibernate.isInitialized(getBooks()) ? getBooks() : "NOT INITIALIZED") +
                '}';
    }
}
