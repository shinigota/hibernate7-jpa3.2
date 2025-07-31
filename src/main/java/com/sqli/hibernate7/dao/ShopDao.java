package com.sqli.hibernate7.dao;

import com.sqli.hibernate7.entity.Book_;
import com.sqli.hibernate7.entity.Shop;
import com.sqli.hibernate7.entity.Shop_;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.FindOption;
import org.hibernate.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopDao extends AbstractDao<Shop, Long>{
    public ShopDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Shop> findAll_withEmployees() {
        return super.findAllWithGraph("Shop.withEmployees");
    }

    public List<Shop> findAll_withBooksAndTheirAuthor() {
        return super.findAllWithGraph("Shop.withBooksAndTheirAuthor");
    }

    public List<Shop> findAllByOwnerId(Long ownerId) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Shop> shops = session.findMultiple(Shop.class, List.of(1L, 2L), new BatchSize(50), CacheMode.);
        session.find(Shop.class, 1L, ReadOnlyMode.READ_ONLY, LockMode.NONE, CacheMode.IGNORE);
        List<Shop> shops =  this.sessionFactory.callInTransaction( em ->
                em.createNamedQuery(Shop_.QUERY_SHOP_FIND_ALL_BY_OWNER_ID)
                        .setParameter(Shop_.ID, ownerId)
                        .setHint("jakarta.persistence.fetchgraph",  em.getEntityGraph("Shop.withEmployees"))
                        .getResultList());

        return shops;
    }

    public List<Shop> findAll_withBooksAndTheirAuthor_withProgrammaticEntityGraph() {
        return this.sessionFactory.callInTransaction(em -> {
            EntityGraph<Shop> eg = em.createEntityGraph(Shop.class);

            eg.addElementSubgraph(Shop_.books)
                    .addAttributeNodes(Book_.author, Book_.title);

            return em.createQuery("FROM Shop", Shop.class)
                    .setHint("jakarta.persistence.fetchgraph", eg)
                    .getResultList();
        });
    }
}
