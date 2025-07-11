package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderProduct op WHERE op.id.order.id IN (SELECT o.id FROM Order o WHERE o.client.id = :clientId)")
    void deleteItemsByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.order.id IN (SELECT o.id FROM Order o WHERE o.client.id = :clientId)")
    void deletePaymentsByClientId(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order o WHERE o.client.id = :userId")
    void deleteOrdersByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO tb_user (name, email, password, phone, country, role)
        VALUES (:#{#user.name}, :#{#user.email}, :#{#user.password}, :#{#user.phone}, :#{#user.country}, :#{#user.role})
    """, nativeQuery = true)
    void insertUser(@Param("user") User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteUserById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :substring, '%'))")
    List<User> findByNameContaining(@Param("substring") String name);

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
