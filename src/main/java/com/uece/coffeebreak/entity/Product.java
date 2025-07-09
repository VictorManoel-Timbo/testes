package com.uece.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uece.coffeebreak.entity.enums.Available;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Available available;

    private String imageUrl;
    private Integer calories;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> items = new HashSet<>();

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompProductStock> composition = new HashSet<>();

    public Product() {}
    public Product(Long id, String name, Double price, Available available, String imageUrl, Integer calories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
        this.imageUrl = imageUrl;
        this.calories = calories;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<CompProductStock> getComposition() {
        return composition;
    }

    @JsonIgnore
    public Set<Order> getOrdes() {
        Set<Order> set = new HashSet<>();
        for (OrderProduct item : items) {
            set.add(item.getOrder());
        }
        return set;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
