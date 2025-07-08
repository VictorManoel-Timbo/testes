package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
