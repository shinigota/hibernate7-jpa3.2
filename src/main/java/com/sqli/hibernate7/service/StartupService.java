package com.sqli.hibernate7.service;

import com.sqli.hibernate7.dao.BookDao;
import com.sqli.hibernate7.dao.EventDao;
import com.sqli.hibernate7.dao.PersonDao;
import com.sqli.hibernate7.dao.ShopDao;
import com.sqli.hibernate7.entity.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StartupService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EventDao eventDao;
    private final PersonDao personDao;
    private final BookDao bookDao;
    private final ShopDao shopDao;

    public StartupService(EventDao eventDao, PersonDao personDao, BookDao bookDao, ShopDao shopDao) {
        this.eventDao = eventDao;
        this.personDao = personDao;
        this.bookDao = bookDao;
        this.shopDao = shopDao;
    }

    @PostConstruct
    public void init() {
//        Event e1 = new Event("Our very first event!", LocalDateTime.now());
//        Event e2 = new Event("A follow up event", LocalDateTime.now());
//        this.eventDao.persist(e1);
//        this.eventDao.persist(e2);
//
//        this.eventDao.find(1L);
//        this.eventDao.find(2L);
//
//        this.eventDao.remove(1L);
//        this.eventDao.remove(2L);
//
//        this.eventDao.find(1L);
//        this.eventDao.find(2L);

        Person p1 = new Person("John", "Doe",
                new Address("Bordeaux", "10 rue truc", "33000"),
                new Address("Pessac", "2 rue bidule", "33600"));
        Person p2 = new Person("Jane", "OUIjbned",
                new Address("Troyes", "10 machin", "10000"),
                new Address("Pont-Sainte-Marie", "20 rue Asd", "10150"));
        Person p3 = new Person("Auteur1", "NomAuteur1", null, null);
        Person p4 = new Person("Auteur1", "NomAuteur1", null, null);
        this.personDao.persist(p1);
        this.personDao.persist(p2);
        this.personDao.persist(p3);
        this.personDao.persist(p4);

        Shop s1 = new Shop(new Address("Bordeaux", "10 avenue de la librairie", "33000"),
                p1,
                List.of(p2, p3),
                new ArrayList<>());
        Shop s2 = new Shop(new Address("Pessac", "2 rue de la bibliotheque", "33600"),
                p3,
                List.of(p4),
                new ArrayList<>());
        this.shopDao.persist(s1);
        this.shopDao.persist(s2);

        Book b1 = new Book("Encyclopédie", p1, List.of(s1));
        Book b2 = new Book("Roman", p4, List.of(s2));
        this.bookDao.persist(b1);
        this.bookDao.persist(b2);

//        logger.info("Test sans entitygraph !");
//        this.bookDao.find(1L);
//        logger.info("Test avec entitygraph !");
//        this.bookDao.findWithGraph(1L);

//        logger.info("all !");
//        List<Shop> l1 = this.shopDao.findAll();
//        logger.info("withEmployees !");
//        List<Shop> l2 = this.shopDao.findAll_withEmployees();
//        logger.info("withBooksAndTheirAuthor !");
//        List<Shop> l3 = this.shopDao.findAll_withBooksAndTheirAuthor();

        List<Shop> byOwnerId = this.shopDao.findAllByOwnerId(1L);

        logger.info("end");
    }
}
