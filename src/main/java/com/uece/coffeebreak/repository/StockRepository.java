package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s")
    List<Stock> findAllStocks();

    @Query("SELECT s FROM Stock s WHERE s.id = :id")
    Optional<Stock> findStockById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM CompProductStock cps WHERE cps.id.stock.id = :stockId")
    void deleteCompositionsByStockId(@Param("stockId") Long stockId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Stock s WHERE s.id = :id")
    void deleteStockById(@Param("id") Long id);

    @Query(value = "SELECT * FROM tb_stock ORDER BY capacity ASC", nativeQuery = true)
    List<Stock> findAllAsc();

    @Query(value = "SELECT * FROM tb_stock ORDER BY capacity DESC", nativeQuery = true)
    List<Stock> findAllDesc();

}
