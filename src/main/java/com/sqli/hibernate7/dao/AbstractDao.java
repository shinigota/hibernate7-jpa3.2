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
        sessionFactory.runInTransaction(session -> session.persist(entity));
    }

    public void remove(U id) {
        sessionFactory.callInTransaction( session -> session)
        sessionFactory.runInTransaction(session -> {
            T entity = session.find(clazz, id);
            session.remove(entity);
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

        Optional<T> val = Optional.ofNullable(sessionFactory.callInTransaction(session -> {
            if (graphName != null) {
                EntityGraph<T> entityGraph = (EntityGraph<T>) session.getEntityGraph(graphName);
                return session.find(entityGraph, id);
            } else {
                return session.find(clazz, id);
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
        List<T> results = sessionFactory.callInTransaction(session -> {
            Query query = session
                    .createQuery("from " + clazz.getSimpleName());
            if (graphName != null) {
                EntityGraph<T> entityGraph = (EntityGraph<T>) session.getEntityGraph(graphName);
                query.setHint("jakarta.persistence.fetchgraph", entityGraph);
            }
            return query.getResultList();
        });

        logger.debug("{} : {} results found", clazz.getSimpleName(), results.size());

        return results;
    }
}
