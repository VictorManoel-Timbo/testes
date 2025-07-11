package com.uece.coffeebreak.view.controller;

import com.uece.coffeebreak.service.StockService;
import com.uece.coffeebreak.shared.StockDTO;
import com.uece.coffeebreak.view.model.request.StockRequest;
import com.uece.coffeebreak.view.model.response.StockResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/stocks")
public class StockController {
    @Autowired
    private StockService service;

    @GetMapping
    public ResponseEntity<List<StockResponse>> findAll() {
        List<StockDTO> stocksDTO = service.findAll();
        List<StockResponse> response = stocksDTO.stream()
                .map(stock -> new ModelMapper().map(stock, StockResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> findById(@PathVariable Long id) {
        StockDTO stockDTO = service.findById(id);
        StockResponse response = new ModelMapper().map(stockDTO, StockResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<StockResponse>> findAllOrdered(@RequestParam(defaultValue = "asc") String direction) {
        List<StockDTO> stocksDTO = service.findAllOrdered(direction);
        List<StockResponse> response = stocksDTO.stream()
                .map(stockDTO -> new ModelMapper().map(stockDTO, StockResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<StockResponse> insert(@RequestBody StockRequest request) {
        ModelMapper mapper = new ModelMapper();
        StockDTO stockDTO = mapper.map(request, StockDTO.class);
        stockDTO = service.insert(stockDTO);
        StockResponse response = mapper.map(stockDTO, StockResponse.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(stockDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponse> update(@PathVariable Long id, @RequestBody StockRequest request) {
        ModelMapper mapper = new ModelMapper();
        StockDTO stockDTO = mapper.map(request, StockDTO.class);
        stockDTO = service.update(id, stockDTO);
        StockResponse response = mapper.map(stockDTO, StockResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
