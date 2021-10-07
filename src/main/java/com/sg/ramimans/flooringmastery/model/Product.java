package com.sg.ramimans.flooringmastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 26, 2021
 * purpose: 
 */
public class Product {
    String product;
    BigDecimal cost;
    BigDecimal labour;
            
    public Product(String productType, BigDecimal costPerSqFt, BigDecimal labourCostPerSqFt) {
        this.product = productType;
        this.cost = costPerSqFt.setScale(2, RoundingMode.HALF_UP);
        this.labour = labourCostPerSqFt.setScale(2, RoundingMode.HALF_UP);
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public BigDecimal getLabour() {
        return labour;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.product);
        hash = 37 * hash + Objects.hashCode(this.cost);
        hash = 37 * hash + Objects.hashCode(this.labour);
        return hash;
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        if (!Objects.equals(this.labour, other.labour)) {
            return false;
        }
        return true;
    }
    
}
