package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.PaymentService;
import com.uece.coffeebreak.shared.PaymentDTO;
import com.uece.coffeebreak.view.model.request.PaymentRequest;
import com.uece.coffeebreak.view.model.response.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> findAll() {
        List<PaymentDTO> paymentsDTO = service.findAll();
        List<PaymentResponse> response = paymentsDTO.stream()
                .map(pay -> new ModelMapper().map(pay, PaymentResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable Long id) {
        PaymentDTO paymentDTO = service.findById(id);
        PaymentResponse response = new ModelMapper().map(paymentDTO, PaymentResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> insert(@RequestBody PaymentRequest request) {
        PaymentDTO paymentDTO = new ModelMapper().map(request, PaymentDTO.class);
        paymentDTO = service.insert(paymentDTO);
        PaymentResponse response = new ModelMapper().map(paymentDTO, PaymentResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(paymentDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> update(@PathVariable Long id, @RequestBody PaymentRequest request) {
        PaymentDTO paymentDTO = new ModelMapper().map(request, PaymentDTO.class);
        paymentDTO = service.update(id, paymentDTO);
        PaymentResponse response = new ModelMapper().map(paymentDTO, PaymentResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
