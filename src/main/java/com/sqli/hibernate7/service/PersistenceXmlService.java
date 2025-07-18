package com.sqli.hibernate7.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PersistenceXmlService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext(unitName = "com.sqli.hibernate7-xml")
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            String dbInfo = (String) entityManager
                .createNativeQuery("SELECT H2VERSION() as version")
                .getSingleResult();
            
            System.out.println("Version H2 : " + dbInfo);
            
        } catch (Exception e) {
            logger.error("Connection error ",  e);
        }
    }
}
