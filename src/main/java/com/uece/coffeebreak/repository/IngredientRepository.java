package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("SELECT i FROM Ingredient i")
    List<Ingredient> findAllIngredients();

    @Query("SELECT i FROM Ingredient i WHERE i.id = :id")
    Optional<Ingredient> findIngredientById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM CompProductStock cps WHERE cps.id.ingredient.id = :ingredientId")
    void deleteCompositionsByIngredientId(@Param("ingredientId") Long ingredientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingredient i WHERE i.id = :id")
    void deleteIngredientById(@Param("id") Long id);

    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :substring, '%'))")
    List<Ingredient> findByNameContaining(@Param("substring") String name);

    @Query(value = """
        SELECT i.id, i.name, COUNT(cps.product_id) AS product_count
        FROM tb_ingredient i
        JOIN tb_comp_product_stock cps ON cps.ingredient_id = i.id
        GROUP BY i.id, i.name
        HAVING COUNT(cps.product_id) > 2
    """, nativeQuery = true)
    List<Object[]> findMoreUsed();

}
