package com.uece.coffeebreak.entity.pk;

import com.uece.coffeebreak.entity.Ingredient;
import com.uece.coffeebreak.entity.Product;
import com.uece.coffeebreak.entity.Stock;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class CompProductStockPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stock == null) ? 0 : stock.hashCode());
        result = prime * result + ((product == null) ? 0 : product.hashCode());
        result = prime * result + ((ingredient == null) ? 0 : ingredient.hashCode());
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
        CompProductStockPK other = (CompProductStockPK) obj;
        if (stock == null) {
            if (other.stock != null) {
                return false;
            }
        } else if (!stock.equals(other.stock)) {
            return false;
        }
        if (product == null) {
            if (other.product != null) {
                return false;
            }
        } else if (!product.equals(other.product)) {
            return false;
        }
        if (ingredient == null) {
            if (other.ingredient != null) {
                return false;
            }
        } else if (!ingredient.equals(other.ingredient)) {
            return false;
        }
        return true;
    }
}
