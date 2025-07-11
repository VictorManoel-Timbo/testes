package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    List<Category> findAllCategories();

    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Optional<Category> findCategoryById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.id = :id")
    void deleteCategoryById(@Param("id") Long id);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :substring, '%'))")
    List<Category> findByNameContaining(@Param("substring") String name);
}
