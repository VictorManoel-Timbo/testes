package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p")
    List<Payment> findAllPayments();

    @Query("SELECT p FROM Payment p WHERE p.id = :id")
    Optional<Payment> findPaymentById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.id = :id")
    void deletePaymentById(@Param("id") Long id);
}
