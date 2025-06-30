package com.sqli.hibernate7.dao;

import com.sqli.hibernate7.entity.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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


//        List<Shop> shops = this.sessionFactory.callInTransaction(em -> {
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Shop> cr = cb.createQuery(Shop.class);
//            Root<Shop> root = cr.from(Shop.class);
//            cr.select(root);
//            cr.where(
//                    cb.and(
//                            cb.equal(root.get(Shop_.owner), ownerId),
//                            cb.equal(root.get(Shop_.address).get(Address_.city), "Pessac")));
//
//            return em
//                    .createQuery(cr)
//                    .setHint("jakarta.persistence.fetchgraph", em.getEntityGraph(Shop_.GRAPH_SHOP_WITH_EMPLOYEES_NATIVE_JPA))
//                    .getResultList();
//
//        });
        List<Shop> shops =  this.sessionFactory.callInTransaction( em ->
                em.createNamedQuery(Shop_.QUERY_SHOP_FIND_ALL_BY_OWNER_ID)
                        .setParameter(Shop_.ID, ownerId)
                        .setHint("jakarta.persistence.fetchgraph", "Shop.withEmployees")
                        .getResultList());

        return shops;
    }
}
