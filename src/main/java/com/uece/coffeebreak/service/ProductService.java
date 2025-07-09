package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.*;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.CategoryRepository;
import com.uece.coffeebreak.repository.IngredientRepository;
import com.uece.coffeebreak.repository.ProductRepository;
import com.uece.coffeebreak.repository.StockRepository;
import com.uece.coffeebreak.shared.*;
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
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ModelMapper().map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return new ModelMapper().map(product, ProductDTO.class);
    }

    public List<ProductDTO> findByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        return products.stream()
                .map(product -> new ModelMapper().map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    public ProductDTO insert(ProductDTO productDTO) {
        productDTO.setId(null);
        Product product = new ModelMapper().map(productDTO, Product.class);

        product.getComposition().clear();
        for (CompProductStockDTO compDTO : productDTO.getComposition()) {
            addCompositionToProduct(product, compDTO);
        }

        product = productRepository.save(product);
        productDTO.setId(product.getId());
        return productDTO;
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        productDTO.setId(id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        Category category = product.getCategory();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(productDTO, product);

        product.setCategory(category);

        if (product.getComposition() != null) {
            product.getComposition().clear();
            for (CompProductStockDTO compDTO : productDTO.getComposition()) {
                if (compDTO.getStock() == null || compDTO.getStock().getId() == null) {
                    throw new ResourceNotFoundException("Stock with id " + compDTO.getStock().getId() + " not found");
                }
                if (compDTO.getIngredient() == null || compDTO.getIngredient().getId() == null) {
                    throw new ResourceNotFoundException("Ingredient with id " + compDTO.getIngredient().getId() + " not found");
                }

                addCompositionToProduct(product, compDTO);
            }
        }

        product = productRepository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    private void addCompositionToProduct(Product product, CompProductStockDTO compDTO) {
        Stock stock = stockRepository.findById(compDTO.getStock().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + compDTO.getStock().getId() + " not found"));

        Ingredient ingredient = ingredientRepository.findById(compDTO.getIngredient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient with id " + compDTO.getStock().getId() + " not found"));

        CompProductStock composition = new CompProductStock(
                product,
                stock,
                ingredient,
                compDTO.getQuantityRequired(),
                compDTO.getReplaceable(),
                compDTO.getUseType(),
                compDTO.getReusable()
        );
        product.getComposition().add(composition);
    }

    public void delete(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ProductDTO fromRequest(ProductRequest request) {
        ModelMapper mapper = new ModelMapper();
        ProductDTO productDTO = new ProductDTO();

        productDTO.setName(request.getName());
        productDTO.setPrice(request.getPrice());
        productDTO.setAvailable(request.getAvailable());
        productDTO.setImageUrl(request.getImageUrl());
        productDTO.setCalories(request.getCalories());

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found"));

        CategoryDTO categoryDTO = mapper.map(category, CategoryDTO.class);
        productDTO.setCategory(categoryDTO);

        List<CompProductStockDTO> compositionsDTO = request.getComposition().stream().map(compReq -> {
            Stock stock = stockRepository.findById(compReq.getStockId())
                    .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + compReq.getStockId() + " not found"));
            Ingredient ingredient = ingredientRepository.findById(compReq.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient with id " + compReq.getIngredientId() + " not found"));
            StockDTO stockDTO = mapper.map(stock, StockDTO.class);
            IngredientDTO ingredientDTO = mapper.map(ingredient, IngredientDTO.class);

            CompProductStockDTO compDTO = new CompProductStockDTO();
            compDTO.setStock(stockDTO);
            compDTO.setIngredient(ingredientDTO);
            compDTO.setQuantityRequired(compReq.getQuantityRequired());
            compDTO.setReplaceable(compReq.getReplaceable());
            compDTO.setReusable(compReq.getReusable());
            compDTO.setUseType(compReq.getUseType());

            return compDTO;
        }).collect(Collectors.toList());

        productDTO.setComposition(compositionsDTO);

        return productDTO;
    }

}
