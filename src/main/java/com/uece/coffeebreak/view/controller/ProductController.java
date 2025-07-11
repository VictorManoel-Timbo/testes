package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.ProductService;
import com.uece.coffeebreak.shared.ProductDTO;
import com.uece.coffeebreak.shared.ProductSalesDTO;
import com.uece.coffeebreak.view.model.request.ProductRequest;
import com.uece.coffeebreak.view.model.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductDTO> productsDTO = service.findAll();
        List<ProductResponse> response = productsDTO.stream()
                .map(productDTO -> new ModelMapper().map(productDTO, ProductResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        ProductDTO productDTO = service.findById(id);
        ProductResponse response = new ModelMapper().map(productDTO, ProductResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> findByName(@RequestParam(value = "name") String name) {
        List<ProductDTO> productsDTO = service.findByName(name);
        ModelMapper mapper = new ModelMapper();
        List<ProductResponse> response = productsDTO.stream()
                .map(productDTO -> mapper.map(productDTO, ProductResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/sales")
    public ResponseEntity<List<ProductSalesDTO>> findPopularProducts() {
        List<ProductSalesDTO> productSalesDTO = service.findPopularProducts();
        return ResponseEntity.ok().body(productSalesDTO);
    }

    @GetMapping("/price-greater")
    public ResponseEntity<List<ProductResponse>> findProductsPriceGreaterCategory() {
        List<ProductDTO> productsDTO = service.findProductsPriceGreaterCategory();
        List<ProductResponse> response = productsDTO.stream()
                .map(productDTO -> new ModelMapper().map(productDTO, ProductResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/calories-greater")
    public ResponseEntity<List<ProductResponse>> findProductsCaloriesGreaterCategory() {
        List<ProductDTO> productsDTO = service.findProductsCaloriesGreaterCategory();
        List<ProductResponse> response = productsDTO.stream()
                .map(productDTO -> new ModelMapper().map(productDTO, ProductResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> insert(@RequestBody ProductRequest request) {
        ProductDTO productDTO = service.fromRequest(request);
        productDTO = service.insert(productDTO);
        ProductResponse response = new ModelMapper().map(productDTO, ProductResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductResponse>> insertAll(@RequestBody List<ProductRequest> request) {
        List<ProductDTO> productsDTO = request.stream().map(req -> service.fromRequest(req)).toList();
        productsDTO = service.insertAll(productsDTO);
        List<ProductResponse> response = productsDTO.stream()
                .map(productDTO -> new ModelMapper().map(productDTO, ProductResponse.class))
                .toList();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        ProductDTO productDTO = service.fromRequest(request);
        productDTO = service.update(id, productDTO);
        ProductResponse response = new ModelMapper().map(productDTO, ProductResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
