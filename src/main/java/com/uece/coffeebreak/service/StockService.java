package com.uece.coffeebreak.service;

import com.uece.coffeebreak.entity.Stock;
import com.uece.coffeebreak.entity.exception.DatabaseException;
import com.uece.coffeebreak.entity.exception.ResourceNotFoundException;
import com.uece.coffeebreak.repository.StockRepository;
import com.uece.coffeebreak.shared.StockDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Autowired
    private StockRepository repository;

    public List<StockDTO> findAll() {
        List<Stock> stocks = repository.findAllStocks();
        return stocks.stream()
                .map(stock -> new ModelMapper().map(stock, StockDTO.class))
                .collect(Collectors.toList());
    }

    public StockDTO findById(Long id) {
        Stock stock = repository.findStockById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + id + " not found"));
        return new ModelMapper().map(stock, StockDTO.class);
    }

    public List<StockDTO> findAllOrdered(String direction) {
        List<Stock> stocks;
        if ("desc".equalsIgnoreCase(direction)) {
            stocks = repository.findAllDesc();
        } else {
            stocks = repository.findAllAsc();
        }
        return stocks.stream()
                .map(stock -> new ModelMapper().map(stock, StockDTO.class))
                .collect(Collectors.toList());
    }

    public StockDTO insert(StockDTO stockDTO) {
        stockDTO.setId(null);
        Stock stock = new ModelMapper().map(stockDTO, Stock.class);
        stock = repository.save(stock);
        stockDTO.setId(stock.getId());
        return stockDTO;
    }

    public StockDTO update(Long id, StockDTO stockDTO) {
        stockDTO.setId(id);
        Stock stock = repository.findStockById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + id + " not found"));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.map(stockDTO, stock);
        stock = repository.save(stock);
        return mapper.map(stock, StockDTO.class);
    }

    public void delete(Long id) {
        try {
            repository.findStockById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Stock with id " + id + " not found"));
            repository.deleteCompositionsByStockId(id);
            repository.deleteStockById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
