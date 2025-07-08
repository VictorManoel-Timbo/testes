package com.uece.coffeebreak.shared;

import com.uece.coffeebreak.entity.enums.Available;

import java.util.List;

public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Available available;
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
