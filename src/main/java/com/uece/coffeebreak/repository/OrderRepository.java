package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o")
    List<Order> findAllOrders();

    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findOrderById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.order.id = :orderId")
    void deletePaymentByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderProduct op WHERE op.id.order.id = :orderId")
    void deleteItemsByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order o WHERE o.id = :id")
    void deleteOrderById(@Param("id") Long id);

}
