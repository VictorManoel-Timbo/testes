package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.*;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.OrderRepository;
import com.uece.coffeebreak.repository.ProductRepository;
import com.uece.coffeebreak.repository.UserRepository;
import com.uece.coffeebreak.shared.OrderDTO;
import com.uece.coffeebreak.shared.OrderProductDTO;
import com.uece.coffeebreak.shared.PaymentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream()
                .map(order -> new ModelMapper().map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        return new ModelMapper().map(order, OrderDTO.class);
    }

    public OrderDTO insert(OrderDTO orderDTO) {
        Order order = new ModelMapper().map(orderDTO, Order.class);

        User client = userRepository.findById(orderDTO.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        order.setClient(client);

        order.getItems().clear();
        for (OrderProductDTO itemDTO : orderDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            OrderProduct item = new OrderProduct(
                    order,
                    product,
                    itemDTO.getQuantity(),
                    itemDTO.getPrice(),
                    itemDTO.getDiscount(),
                    itemDTO.getObservation()
            );
            order.getItems().add(item);
        }

        if (orderDTO.getPayment() != null) {
            Payment payment = new ModelMapper().map(orderDTO.getPayment(), Payment.class);
            payment.setOrder(order);
            order.setPayment(payment);
        }

        order = orderRepository.save(order);
        return new ModelMapper().map(order, OrderDTO.class);
    }

    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(orderDTO, order);
        if (orderDTO.getItems() != null) {
            order.getItems().clear();
            for (OrderProductDTO itemDTO : orderDTO.getItems()) {
                if (itemDTO.getProduct() == null || itemDTO.getProduct().getId() == null) {
                    throw new ResourceNotFoundException("Product ID is required for each item");
                }
                Product product = productRepository.findById(itemDTO.getProduct().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + itemDTO.getProduct().getId() + " not found"));
                OrderProduct item = new OrderProduct(
                        order,
                        product,
                        itemDTO.getQuantity(),
                        itemDTO.getPrice(),
                        itemDTO.getDiscount(),
                        itemDTO.getObservation()
                );
                order.getItems().add(item);
            }
        }
        if (orderDTO.getPayment() != null) {
            PaymentDTO payDTO = orderDTO.getPayment();
            Payment payment = new Payment();
            mapper.map(payDTO, payment);
            payment.setOrder(order);
            order.setPayment(payment);
        }
        order.getTotal();
        order = orderRepository.save(order);
        return mapper.map(order, OrderDTO.class);
    }

    public void delete(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
