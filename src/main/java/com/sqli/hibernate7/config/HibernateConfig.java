package com.sqli.hibernate7.config;

import com.sqli.hibernate7.entity.Book;
import com.sqli.hibernate7.entity.Event;
import com.sqli.hibernate7.entity.Person;
import com.sqli.hibernate7.entity.Shop;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HibernateConfig {
    private static final Class[] CLASSES_TO_MAP = {
            Event.class,
            Person.class,
            Book.class,
            Shop.class,
    };

    @Bean
    @Primary
    public SessionFactory sessionFactory() {
        return new HibernatePersistenceConfiguration("com.sqli.hibernate7")
                .managedClasses(CLASSES_TO_MAP)
                .createEntityManagerFactory();
    }
}
