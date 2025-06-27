package com.sqli.hibernate7.dao;

import com.sqli.hibernate7.entity.Book;
import com.sqli.hibernate7.entity.Shop;
import com.sqli.hibernate7.entity.Shop_;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.NamedEntityGraph;
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
        return this.sessionFactory.callInTransaction( em ->
                em.createNamedQuery(Shop_.QUERY_SHOP_FIND_ALL_BY_OWNER_ID)
                        .setParameter("id", ownerId)
                        .setHint("jakarta.persistence.fetchgraph", "Shop.withEmployees")
                        .getResultList());
    }
}
