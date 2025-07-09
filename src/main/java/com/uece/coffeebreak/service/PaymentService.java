package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.Order;
import com.uece.coffeebreak.entity.Payment;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.OrderRepository;
import com.uece.coffeebreak.repository.PaymentRepository;
import com.uece.coffeebreak.shared.PaymentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<PaymentDTO> findAll() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(pay -> new ModelMapper().map(pay, PaymentDTO.class))
                .collect(Collectors.toList());
    }

    public PaymentDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with id " + id + " not found"));
        return new ModelMapper().map(payment, PaymentDTO.class);
    }

    public PaymentDTO insert(PaymentDTO paymentDTO) {
        paymentDTO.setId(null);
        Payment payment = new ModelMapper().map(paymentDTO, Payment.class);
        if (paymentDTO.getOrderId() != null) {
            Order order = orderRepository.findById(paymentDTO.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order with id " + paymentDTO.getOrderId() + " not found"));
            payment.setOrder(order);
        }
        payment = paymentRepository.save(payment);
        paymentDTO.setId(payment.getId());
        return paymentDTO;
    }

    public PaymentDTO update(Long id, PaymentDTO paymentDTO) {
        paymentDTO.setId(id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(paymentDTO, payment);
        if (paymentDTO.getOrderId() != null && !payment.getOrder().getId().equals(paymentDTO.getOrderId())) {
            throw new DatabaseException("Cannot change the associated order of a payment.");
        }
        paymentRepository.save(payment);
        return mapper.map(payment, PaymentDTO.class);
    }

    public void delete(Long id) {
        try {
            Payment payment = paymentRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Payment with id " + id + " not found"));
            Order order = payment.getOrder();
            order.setPayment(null);
            orderRepository.save(payment.getOrder());
            paymentRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
