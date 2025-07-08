package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.Category;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.CategoryRepository;
import com.uece.coffeebreak.shared.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<CategoryDTO> findAll() {
        List<Category> categories = repository.findAll();
        return categories.stream()
                .map(cat -> new ModelMapper().map(cat, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> findByName(String name) {
        List<Category> categories = repository.findByNameContaining(name);
        return categories.stream()
                .map(cat -> new ModelMapper().map(cat, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO insert(CategoryDTO categoryDTO) {
        categoryDTO.setId(null);
        Category category = new ModelMapper().map(categoryDTO, Category.class);
        category = repository.save(category);
        categoryDTO.setId(category.getId());
        return categoryDTO;
    }

    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(categoryDTO, category);
        category = repository.save(category);
        return mapper.map(category, CategoryDTO.class);
    }

    public void delete(Long id) {
        try {
            Category category = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
