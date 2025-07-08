package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.IngredientService;
import com.uece.coffeebreak.shared.IngredientDTO;
import com.uece.coffeebreak.view.model.request.IngredientRequest;
import com.uece.coffeebreak.view.model.response.IngredientResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService service;

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> findAll() {
        List<IngredientDTO> ingredientsDTO = service.findAll();
        List<IngredientResponse> response = ingredientsDTO.stream()
                .map(ingredientDTO -> new ModelMapper().map(ingredientDTO, IngredientResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> findById(@PathVariable Long id) {
        IngredientDTO ingredientDTO = service.findById(id);
        IngredientResponse response = new ModelMapper().map(ingredientDTO, IngredientResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> insert(@RequestBody IngredientRequest request) {
        ModelMapper mapper = new ModelMapper();
        IngredientDTO ingredientDTO = mapper.map(request, IngredientDTO.class);
        ingredientDTO = service.insert(ingredientDTO);
        IngredientResponse response = mapper.map(ingredientDTO, IngredientResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ingredientDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> update(@PathVariable Long id, @RequestBody IngredientRequest request) {
        ModelMapper mapper = new ModelMapper();
        IngredientDTO ingredientDTO = mapper.map(request, IngredientDTO.class);
        ingredientDTO = service.update(id, ingredientDTO);
        IngredientResponse response = mapper.map(ingredientDTO, IngredientResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
