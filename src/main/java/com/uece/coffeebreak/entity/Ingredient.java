package com.uece.coffeebreak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uece.coffeebreak.entity.enums.IngredientType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_ingredient")
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private IngredientType type;

    @Column(nullable = false)
    private Integer minimumCapacity;

    private String unitMeasure;
    private String supplier;

    @JsonIgnore
    @OneToMany(mappedBy = "id.ingredient")
    private Set<CompProductStock> composition = new HashSet<>();

    public Ingredient() {}
    public Ingredient(Long id, String name, IngredientType type, String unitMeasure, String supplier, Integer minimumCapacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unitMeasure = unitMeasure;
        this.supplier = supplier;
        this.minimumCapacity = minimumCapacity;
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

    public Set<CompProductStock> getComposition() {
        return composition;
    }

    public void setComposition(Set<CompProductStock> composition) {
        this.composition = composition;
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
        Ingredient other = (Ingredient) obj;
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
