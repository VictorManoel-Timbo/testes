package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
