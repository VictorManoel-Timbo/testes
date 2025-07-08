package com.uece.coffeebreak.view.model.request;

import com.uece.coffeebreak.entity.enums.Available;

import java.util.List;

public class ProductRequest {
    private String name;
    private Double price;
    private Available available;
    private String imageUrl;
    private Integer calories;
    private Long categoryId;
    private List<CompProductStockRequest> composition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Available getAvailable() {
        return available;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<CompProductStockRequest> getComposition() {
        return composition;
    }

    public void setComposition(List<CompProductStockRequest> composition) {
        this.composition = composition;
    }
}
