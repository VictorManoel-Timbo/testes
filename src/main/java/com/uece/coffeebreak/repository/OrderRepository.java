package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
