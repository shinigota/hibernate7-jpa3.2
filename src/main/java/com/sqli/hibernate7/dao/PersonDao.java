package com.sqli.hibernate7.dao;

import com.sqli.hibernate7.entity.Person;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonDao extends AbstractDao<Person, Long>{
    public PersonDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
