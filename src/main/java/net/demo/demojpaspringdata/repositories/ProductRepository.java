package net.demo.demojpaspringdata.repositories;

import net.demo.demojpaspringdata.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // SELECT * FROM products WHERE name LIKE %keyword%
    List<Product> findByNameContains(String keyword);
}
