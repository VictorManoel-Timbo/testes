package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.CategoryService;
import com.uece.coffeebreak.shared.CategoryDTO;
import com.uece.coffeebreak.view.model.request.CategoryRequest;
import com.uece.coffeebreak.view.model.response.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryDTO> categoriesDTO = service.findAll();
        ModelMapper mapper = new ModelMapper();
        List<CategoryResponse> response = categoriesDTO.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        CategoryDTO categoryDTO = service.findById(id);
        CategoryResponse response = new ModelMapper().map(categoryDTO, CategoryResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> findByName(@RequestParam(value = "name") String name) {
        List<CategoryDTO> categoriesDTO = service.findByName(name);
        ModelMapper mapper = new ModelMapper();
        List<CategoryResponse> response = categoriesDTO.stream()
                .map(cat -> mapper.map(cat, CategoryResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@RequestBody CategoryRequest request) {
        ModelMapper mapper = new ModelMapper();
        CategoryDTO categoryDTO = mapper.map(request, CategoryDTO.class);
        categoryDTO = service.insert(categoryDTO);
        CategoryResponse response = mapper.map(categoryDTO, CategoryResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        ModelMapper mapper = new ModelMapper();
        CategoryDTO dto = mapper.map(request, CategoryDTO.class);
        dto = service.update(id, dto);
        CategoryResponse response = mapper.map(dto, CategoryResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
