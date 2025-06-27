@NamedEntityGraph(name = "Shop.withEmployees", graph = "employees")
@NamedEntityGraph(name = "Shop.withBooksAndTheirAuthor", graph = "books(author)")
package com.sqli.hibernate7.dao;

import org.hibernate.annotations.NamedEntityGraph;
