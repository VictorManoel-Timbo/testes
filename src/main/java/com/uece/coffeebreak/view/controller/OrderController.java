package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.OrderService;
import com.uece.coffeebreak.shared.OrderDTO;
import com.uece.coffeebreak.view.model.request.OrderRequest;
import com.uece.coffeebreak.view.model.response.OrderResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        List<OrderDTO> orders = service.findAll();
        ModelMapper mapper = new ModelMapper();
        List<OrderResponse> response = orders.stream()
                .map(order -> mapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        OrderDTO orderDTO = service.findById(id);
        OrderResponse response = new ModelMapper().map(orderDTO, OrderResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> insert(@RequestBody OrderRequest request) {
        OrderDTO orderDTO = service.fromRequest(request);
        orderDTO = service.insert(orderDTO);
        OrderResponse response = new ModelMapper().map(orderDTO, OrderResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(orderDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody OrderRequest request) {
        OrderDTO dto = service.fromRequest(request);
        dto = service.update(id, dto);
        OrderResponse response = new ModelMapper().map(dto, OrderResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
