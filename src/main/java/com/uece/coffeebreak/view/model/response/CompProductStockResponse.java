package com.uece.coffeebreak.view.model.response;

public class CompProductStockResponse {
    private Integer quantityRequired;
    private Boolean replaceable;
    private String useType;
    private Boolean reusable;
    private StockResponse stock;
    private IngredientResponse ingredient;

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

    public StockResponse getStock() {
        return stock;
    }

    public void setStock(StockResponse stock) {
        this.stock = stock;
    }

    public IngredientResponse getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientResponse ingredient) {
        this.ingredient = ingredient;
    }
}
