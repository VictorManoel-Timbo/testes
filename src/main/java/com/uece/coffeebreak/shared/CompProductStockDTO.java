package com.uece.coffeebreak.shared;

public class CompProductStockDTO {
    private Integer quantityRequired;
    private Boolean replaceable;
    private String useType;
    private Boolean reusable;
    private StockDTO stock;
    private IngredientDTO ingredient;

    public Integer getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(Integer quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    public Boolean getReplaceable() {
        return replaceable;
    }

    public void setReplaceable(Boolean replaceable) {
        this.replaceable = replaceable;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public Boolean getReusable() {
        return reusable;
    }

    public void setReusable(Boolean reusable) {
        this.reusable = reusable;
    }

    public StockDTO getStock() {
        return stock;
    }

    public void setStock(StockDTO stock) {
        this.stock = stock;
    }

    public IngredientDTO getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientDTO ingredient) {
        this.ingredient = ingredient;
    }
}
