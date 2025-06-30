package com.sqli.hibernate7.dao;

import com.sqli.hibernate7.entity.Book;
import com.sqli.hibernate7.entity.Person;
import jakarta.persistence.EntityGraph;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service

public class BookDao extends AbstractDao<Book, Long>{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BookDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


//    session.find(Book.class, id, Collections.singletonMap(
//            "jakarta.persistence.fetchgraph",
//            session.getEntityGraph("basic_book")
//            ))

    public List<Book> findAll_eagerAuthor() {
        return super.findAllWithGraph("Book");
    }

    public Optional<Book> findWithGraph(Long id) {
        AtomicReference<Book> entity = new AtomicReference<>();
        sessionFactory.runInTransaction(session -> {
            EntityGraph<Book> entityGraph = (EntityGraph<Book>) session.getEntityGraph("book.basic_book");


            entity.set(    session.find(entityGraph, id));
        });
        Optional<Book> val = Optional.ofNullable(entity.get());

        if (logger.isDebugEnabled()) {
            val.ifPresentOrElse(
                    event -> logger.debug(event.toString()),
                    () -> logger.debug("Event with id {} not found", id));

        }

        return val;
    }
}
