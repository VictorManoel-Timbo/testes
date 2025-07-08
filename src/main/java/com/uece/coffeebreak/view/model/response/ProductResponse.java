package com.uece.coffeebreak.view.model.response;

import com.uece.coffeebreak.shared.CategoryDTO;
import com.uece.coffeebreak.shared.CompProductStockDTO;

import java.util.List;

public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private String available;
    private String imageUrl;
    private Integer calories;
    private CategoryDTO category;
    private List<CompProductStockDTO> composition;

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

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
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

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public List<CompProductStockDTO> getComposition() {
        return composition;
    }

    public void setComposition(List<CompProductStockDTO> composition) {
        this.composition = composition;
    }
}
