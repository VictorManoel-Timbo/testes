package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.ProductService;
import com.uece.coffeebreak.shared.ProductDTO;
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
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductDTO> productsDTO = service.findAll();
        List<ProductResponse> response = productsDTO.stream()
                .map(prod -> new ModelMapper().map(prod, ProductResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        ProductDTO productDTO = service.findById(id);
        ProductResponse response = new ModelMapper().map(productDTO, ProductResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> insert(@RequestBody ProductRequest request) {
        ModelMapper mapper = new ModelMapper();
        ProductDTO productDTO = mapper.map(request, ProductDTO.class);
        productDTO = service.insert(productDTO);
        ProductResponse response = mapper.map(productDTO, ProductResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        ModelMapper mapper = new ModelMapper();
        ProductDTO productDTO = mapper.map(request, ProductDTO.class);
        productDTO = service.update(id, productDTO);
        ProductResponse response = mapper.map(productDTO, ProductResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
