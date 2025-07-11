package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :substring,'%'))")
    List<Product> findByNameContaining(@Param("substring") String name);

    @Query("""
        SELECT p.id, p.name, SUM(op.quantity)
        FROM Product p
        LEFT JOIN p.items op
        GROUP BY p.id, p.name
        HAVING SUM(op.quantity) > 1
    """)
    List<Object[]> findPopularProducts();

    @Query(value = """
        SELECT * 
        FROM tb_product p
        WHERE p.price > ANY (
          SELECT p2.price 
          FROM tb_product p2 
          WHERE p2.category_id = p.category_id
          AND p2.id <> p.id
        )
        """, nativeQuery = true)
    List<Product> findProductsPriceGreaterCategory();

    @Query(value = """
        SELECT * 
        FROM tb_product p1
        WHERE COALESCE(p1.calories, 0) > ANY (
            SELECT COALESCE(p2.calories, 0)
            FROM tb_product p2
            WHERE p2.category_id = p1.category_id
              AND p2.id <> p1.id
        )
        """, nativeQuery = true)
    List<Product> findProductsCaloriesGreaterCategory();
}
