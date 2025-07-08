package com.uece.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uece.coffeebreak.entity.pk.CompProductStockPK;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tb_comp_product_stock")
public class CompProductStock implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CompProductStockPK id = new CompProductStockPK();

    @Column(nullable = false)
    private Integer quantityRequired;

    @Column(nullable = false)
    private Boolean replaceable;

    private String useType;
    private Boolean reusable;

    public CompProductStock() {}
    public CompProductStock(Product product, Stock stock, Ingredient ingredient, Integer quantityRequired, Boolean replaceable, String useType, Boolean reusable) {
        id.setProduct(product);
        id.setStock(stock);
        id.setIngredient(ingredient);
        this.quantityRequired = quantityRequired;
        this.replaceable = replaceable;
        this.useType = useType;
        this.reusable = reusable;
    }

    @JsonIgnore
    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Stock getStock() {
        return id.getStock();
    }

    public void setStock(Stock stock) {
        id.setStock(stock);
    }

    public Ingredient getIngredient() {
        return id.getIngredient();
    }

    public void setIngredient(Ingredient ingredient) {
        id.setIngredient(ingredient);
    }

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
}
