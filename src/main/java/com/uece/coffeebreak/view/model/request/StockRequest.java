package com.uece.coffeebreak.view.model.request;

import com.uece.coffeebreak.entity.enums.StockType;

public class StockRequest {
    private StockType type;
    private Integer capacity;
    private Double temperature;
    private String description;

    public StockType getType() {
        return type;
    }

    public void setType(StockType type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
