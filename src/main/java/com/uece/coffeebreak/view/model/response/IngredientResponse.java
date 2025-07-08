package com.uece.coffeebreak.view.model.response;

import com.uece.coffeebreak.entity.enums.IngredientType;

public class IngredientResponse {
    private Long id;
    private String name;
    private IngredientType type;
    private Integer minimumCapacity;
    private String unitMeasure;
    private String supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientType getType() {
        return type;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public Integer getMinimumCapacity() {
        return minimumCapacity;
    }

    public void setMinimumCapacity(Integer minimumCapacity) {
        this.minimumCapacity = minimumCapacity;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
