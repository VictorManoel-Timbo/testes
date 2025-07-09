package com.uece.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uece.coffeebreak.entity.enums.StockType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_stock")
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private StockType type;

    @Column(nullable = false)
    private Integer capacity;

    private Double temperature;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "id.stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompProductStock> composition = new HashSet<CompProductStock>();


    public Stock() {}
    public Stock(Long id ,StockType type, Integer capacity, Double temperature, String description) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.temperature = temperature;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<CompProductStock> getComposition() {
        return composition;
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
        Stock other = (Stock) obj;
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
