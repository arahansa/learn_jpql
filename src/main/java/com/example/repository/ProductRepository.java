package com.example.repository;

import com.example.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arahansa on 2016-01-05.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
