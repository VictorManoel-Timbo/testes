package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.Ingredient;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.IngredientRepository;
import com.uece.coffeebreak.shared.IngredientDTO;
import com.uece.coffeebreak.shared.IngredientUsageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository repository;

    public List<IngredientDTO> findAll() {
        List<Ingredient> ingredients = repository.findAllIngredients();
        return ingredients.stream()
                .map(ingredient -> new ModelMapper().map(ingredient, IngredientDTO.class))
                .collect(Collectors.toList());
    }

    public IngredientDTO findById(Long id) {
        Ingredient ingredient = repository.findIngredientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient with id " + id + " not found"));
        return new ModelMapper().map(ingredient, IngredientDTO.class);
    }

    public List<IngredientDTO> findByName(String name) {
        List<Ingredient> ingredients = repository.findByNameContaining(name);
        return ingredients.stream()
                .map(ingredient -> new ModelMapper().map(ingredient, IngredientDTO.class))
                .collect(Collectors.toList());
    }

    public List<IngredientUsageDTO> findMoreUsed() {
        List<Object[]> results = repository.findMoreUsed();
        return results.stream()
                .map(row -> new IngredientUsageDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }

    public IngredientDTO insert(IngredientDTO ingredientDTO) {
        ingredientDTO.setId(null);
        Ingredient ingredient = new ModelMapper().map(ingredientDTO, Ingredient.class);
        ingredient = repository.save(ingredient);
        ingredientDTO.setId(ingredient.getId());
        return ingredientDTO;
    }

    public IngredientDTO update(Long id, IngredientDTO ingredientDTO) {
        ingredientDTO.setId(id);
        Ingredient ingredient = repository.findIngredientById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(ingredientDTO, ingredient);
        ingredient = repository.save(ingredient);
        return mapper.map(ingredient, IngredientDTO.class);
    }

    public void delete(Long id) {
        try {
            repository.findIngredientById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient with id " + id + " not found"));
            repository.deleteCompositionsByIngredientId(id);
            repository.deleteIngredientById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
