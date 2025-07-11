package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String name);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :substring, '%'))")
    List<User> findByNameContaining(@Param("substring") String name);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u.id, u.name, COUNT(o.id)
        FROM User u
        JOIN u.orders o
        GROUP BY u.id, u.name
    """)
    List<Object[]> countOrdersByUser();

    @Query("""
        SELECT u FROM User u
        WHERE u.role = com.uece.coffeebreak.entity.enums.Role.CLIENT
        AND u.id IN (
            SELECT o.client.id FROM Order o
            JOIN o.items op
            GROUP BY o.client.id
            HAVING SUM(op.price * op.quantity) > ALL (
                SELECT SUM(op2.price * op2.quantity) FROM Order o2
                JOIN o2.items op2
                WHERE o2.client.id = :clientId
                GROUP BY o2.client.id
            )
        )
        """)
    List<User> findClientsOrdersSumGreater(@Param("clientId") Long clientId);
}
