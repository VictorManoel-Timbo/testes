package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :substring, '%'))")
    List<Ingredient> findByNameContaining(@Param("substring") String name);
}
