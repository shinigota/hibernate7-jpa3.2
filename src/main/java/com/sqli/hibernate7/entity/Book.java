package com.sqli.hibernate7.entity;

import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NamedEntityGraph;

import java.util.Collection;

@Entity
@Table(name = "Books")
@NamedEntityGraph(
        graph = "author"
)
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person author;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Shop> shops;

    public Book() {
    }

    public Book(String title, Person author, Collection<Shop> shops) {
        this.title = title;
        this.author = author;
        this.shops = shops;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Person getAuthor() {
        return author;
    }

    public Collection<Shop> getShops() {
        return shops;
    }

    public void setShops(Collection<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + (Hibernate.isInitialized(getTitle()) ? getTitle() : "NOT INITIALIZED") + '\'' +
                ", author=" + (Hibernate.isInitialized(getAuthor()) ? getAuthor() : "NOT INITIALIZED") +
                ", shops=" + (Hibernate.isInitialized(getShops()) ? getShops() : "NOT INITIALIZED") +
                '}';
    }
}
