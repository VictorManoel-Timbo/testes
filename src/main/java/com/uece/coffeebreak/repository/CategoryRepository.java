package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
