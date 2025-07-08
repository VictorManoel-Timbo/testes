package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
