package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.Product;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.ProductRepository;
import com.uece.coffeebreak.shared.ProductDTO;
import com.uece.coffeebreak.view.model.request.ProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<ProductDTO> findAll() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(product -> new ModelMapper().map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return new ModelMapper().map(product, ProductDTO.class);
    }

    public List<ProductDTO> findByName(String name) {
        List<Product> products = repository.findByNameContaining(name);
        return products.stream()
                .map(product -> new ModelMapper().map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO insert(ProductDTO productDTO) {
        productDTO.setId(null);
        Product product = new ModelMapper().map(productDTO, Product.class);
        product = repository.save(product);
        productDTO.setId(product.getId());
        return productDTO;
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        productDTO.setId(id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(productDTO, product);
        product = repository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    public void delete(Long id) {
        try {
            Product product = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProductDTO fromRequest(ProductRequest request) {
        return new ModelMapper().map(request, ProductDTO.class);
    }
}
