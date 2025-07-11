package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.*;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.OrderRepository;
import com.uece.coffeebreak.repository.ProductRepository;
import com.uece.coffeebreak.repository.UserRepository;
import com.uece.coffeebreak.shared.*;
import com.uece.coffeebreak.view.model.request.OrderRequest;
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
        return orderRepository.findAllOrders().stream()
                .map(order -> new ModelMapper().map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
        return new ModelMapper().map(order, OrderDTO.class);
    }

    public OrderDTO insert(OrderDTO orderDTO) {
        orderDTO.setId(null);
        Order order = new ModelMapper().map(orderDTO, Order.class);

        User client = userRepository.findUserById(orderDTO.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + orderDTO.getClient().getId() + " not found"));
        order.setClient(client);

        order.getItems().clear();
        for (OrderProductDTO itemDTO : orderDTO.getItems()) {
            Product product = productRepository.findProductById(itemDTO.getProduct().getId())
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

        if (orderDTO.getPayment() != null) {
            Payment payment = new ModelMapper().map(orderDTO.getPayment(), Payment.class);
            payment.setOrder(order);
            order.setPayment(payment);
        }

        order = orderRepository.save(order);
        return new ModelMapper().map(order, OrderDTO.class);
    }

    public OrderDTO update(Long id, OrderDTO orderDTO) {
        orderDTO.setId(id);
        Order order = orderRepository.findOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));

        User currentClient = order.getClient();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(orderDTO, order);

        order.setClient(currentClient);

        if (orderDTO.getItems() != null) {
            order.getItems().clear();
            for (OrderProductDTO itemDTO : orderDTO.getItems()) {
                if (itemDTO.getProduct() == null || itemDTO.getProduct().getId() == null) {
                    throw new ResourceNotFoundException("Product with id " + itemDTO.getProduct().getId() + " not found");
                }
                Product product = productRepository.findProductById(itemDTO.getProduct().getId())
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
            orderRepository.findOrderById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
            orderRepository.deleteItemsByOrderId(id);
            orderRepository.deletePaymentByOrderId(id);
            orderRepository.deleteOrderById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public OrderDTO fromRequest(OrderRequest request) {
        ModelMapper mapper = new ModelMapper();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setMoment(request.getMoment());
        orderDTO.setStatus(request.getStatus());
        orderDTO.setWithdrawalMethod(request.getWithdrawalMethod());

        User client = userRepository.findUserById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + request.getClientId() + " not found"));
        UserDTO clientDTO = mapper.map(client, UserDTO.class);
        orderDTO.setClient(clientDTO);

        List<OrderProductDTO> items = request.getItems().stream().map(itemReq -> {
            Product product = productRepository.findProductById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product  with id " + itemReq.getProductId() + " not found"));
            ProductDTO productDTO = mapper.map(product, ProductDTO.class);

            OrderProductDTO itemDTO = new OrderProductDTO();
            itemDTO.setProduct(productDTO);
            itemDTO.setQuantity(itemReq.getQuantity());
            itemDTO.setPrice(product.getPrice());
            itemDTO.setDiscount(itemReq.getDiscount());
            itemDTO.setObservation(itemReq.getObservation());

            return itemDTO;
        }).collect(Collectors.toList());

        orderDTO.setItems(items);
        if (request.getPayment() != null) {
            PaymentDTO payDTO = mapper.map(request.getPayment(), PaymentDTO.class);
            orderDTO.setPayment(payDTO);
        }
        return orderDTO;
    }

}
