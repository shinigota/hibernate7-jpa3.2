package com.sqli.hibernate7.dao;

import com.sqli.hibernate7.entity.Event;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class EventDao extends AbstractDao<Event, Long>{
    public EventDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
