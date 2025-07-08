package com.uece.coffeebreak.repository;

import com.uece.coffeebreak.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
