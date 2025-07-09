package com.uece.coffeebreak.view.model.response;

import com.uece.coffeebreak.entity.enums.Available;

import java.util.List;

public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private Available available;
    private String imageUrl;
    private Integer calories;
    private CategoryResponse category;
    private List<CompProductStockResponse> composition;

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

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }

    public List<CompProductStockResponse> getComposition() {
        return composition;
    }

    public void setComposition(List<CompProductStockResponse> composition) {
        this.composition = composition;
    }
}
