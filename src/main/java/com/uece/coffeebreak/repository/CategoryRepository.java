package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :substring, '%'))")
    List<Category> findByNameContaining(@Param("substring") String name);
}
