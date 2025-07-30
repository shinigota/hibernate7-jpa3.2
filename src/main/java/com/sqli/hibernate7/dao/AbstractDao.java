package com.sqli.hibernate7.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T, U> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Class<T> clazz;

    protected final SessionFactory sessionFactory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.clazz = ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
        this.sessionFactory = sessionFactory;
    }

    public void persist(T entity) {
        logger.debug("{} : Persisting entity {}", clazz.getSimpleName(), entity);
        sessionFactory.runInTransaction(entityManager -> entityManager.persist(entity));
    }

    public void remove(U id) {
        sessionFactory.runInTransaction(entityManager -> {
            T entity = entityManager.find(clazz, id);
            entityManager.remove(entity);
            logger.debug("{} : Deleting entity {}", clazz.getSimpleName(), id);
        });
    }

    public Optional<T> find(U id) {
        return find(null, id);
    }

    public Optional<T> findWithGraph(String graphName, U id) {
        return find(graphName, id);
    }

    public List<T> findAll() {
        return findAll(null);
    }

    public List<T> findAllWithGraph(String graphName) {
        return findAll(graphName);
    }

    private Optional<T> find(String graphName, U id) {

        Optional<T> val = Optional.ofNullable(sessionFactory.callInTransaction(entityManager -> {
            if (graphName != null) {
                EntityGraph<T> entityGraph = (EntityGraph<T>) entityManager.getEntityGraph(graphName);
                return entityManager.find(entityGraph, id);
            } else {
                return entityManager.find(clazz, id);
            }
        }));

        if (logger.isDebugEnabled()) {
            val.ifPresentOrElse(
                    event -> logger.debug("{} : {}", clazz.getSimpleName(), event),
                    () -> logger.debug("{} : Entity with id {} not found", clazz.getSimpleName(), id));

        }

        return val;
    }

    private List<T> findAll(String graphName) {
        List<T> results = sessionFactory.callInTransaction(entityManager -> {
            Query query = entityManager
                    .createQuery("from " + clazz.getSimpleName());
            if (graphName != null) {
                EntityGraph<T> entityGraph = (EntityGraph<T>) entityManager.getEntityGraph(graphName);
                query.setHint("jakarta.persistence.fetchgraph", entityGraph);
            }
            return query.getResultList();
        });

        logger.debug("{} : {} results found", clazz.getSimpleName(), results.size());

        return results;
    }
}
