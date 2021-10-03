package com.sg.ramimans.flooringmastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    
}
