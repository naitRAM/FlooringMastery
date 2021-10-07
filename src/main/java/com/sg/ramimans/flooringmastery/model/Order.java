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
public class Order {
    int orderId;
    String customerName;
    String stateCode;
    String productName;
    BigDecimal productRate;
    BigDecimal productLabourRate;
    BigDecimal stateTaxRate;
    BigDecimal area;
    BigDecimal materialCost;
    BigDecimal labourCost;
    BigDecimal tax;
    BigDecimal total;
    
    public Order(String customerName, StateTax state, Product product, BigDecimal area) {
        this.customerName = customerName;
        this.stateCode = state.getStateCode();
        this.productName = product.getProduct();
        this.productRate = product.getCost();
        this.productLabourRate = product.getLabour();
        this.stateTaxRate = state.getTaxRate();
        this.area = area;
        this.calculateTotal();
    }

    

    public Order(int orderId, String customerName, String stateCode, BigDecimal stateTaxRate, String productName, BigDecimal productRate, BigDecimal productLabourRate, BigDecimal area) {
       this.orderId = orderId;
       this.customerName = customerName;
       this.stateCode = stateCode;
       this.stateTaxRate = stateTaxRate.setScale(2, RoundingMode.HALF_UP);
       this.productName = productName;
       this.productRate = productRate.setScale(2, RoundingMode.HALF_UP);
       this.productLabourRate = productLabourRate.setScale(2, RoundingMode.HALF_UP);
       this.area = area.setScale(2, RoundingMode.HALF_UP);
       this.calculateTotal();
    }
    
    private void calculateTotal() {
       this.materialCost = this.area.multiply(this.productRate).setScale(2, RoundingMode.HALF_UP);
       this.labourCost = this.area.multiply(this.productLabourRate).setScale(2, RoundingMode.HALF_UP);
       this.tax = this.stateTaxRate.divide(new BigDecimal("100")).multiply(this.materialCost.add(this.labourCost)).setScale(2, RoundingMode.HALF_UP);
       this.total = this.materialCost.add(this.labourCost).add(this.tax).setScale(2, RoundingMode.HALF_UP);
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStateCode() {
        return this.stateCode;
    }
    
    public BigDecimal getStateTaxRate() {
        return stateTaxRate;
    }

    public String getProductName() {
        return this.productName;
    }
    
    public BigDecimal getProductRate() {
        return this.productRate;
    }
    
    public BigDecimal getProductLabourRate(){
        return this.productLabourRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLabourCost() {
        return labourCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setNewState(StateTax state) {
        this.stateCode = state.stateCode;
        this.stateTaxRate = state.taxRate.setScale(2, RoundingMode.HALF_UP);
        this.calculateTotal();
    }

    public void setNewProduct(Product product) {
        this.productName = product.product;
        this.productRate = product.cost.setScale(2, RoundingMode.HALF_UP);
        this.productLabourRate = product.labour.setScale(2, RoundingMode.HALF_UP);
        this.calculateTotal();
    }

    public void setArea(BigDecimal area) {
        this.area = area.setScale(2, RoundingMode.HALF_UP);
        this.calculateTotal();
    }

    public BigDecimal getTotal() {
        return total;
    }
    
    public void setOrderId(int id) {
        this.orderId = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.orderId;
        hash = 89 * hash + Objects.hashCode(this.customerName);
        hash = 89 * hash + Objects.hashCode(this.stateCode);
        hash = 89 * hash + Objects.hashCode(this.productName);
        hash = 89 * hash + Objects.hashCode(this.productRate);
        hash = 89 * hash + Objects.hashCode(this.productLabourRate);
        hash = 89 * hash + Objects.hashCode(this.stateTaxRate);
        hash = 89 * hash + Objects.hashCode(this.area);
        hash = 89 * hash + Objects.hashCode(this.materialCost);
        hash = 89 * hash + Objects.hashCode(this.labourCost);
        hash = 89 * hash + Objects.hashCode(this.tax);
        hash = 89 * hash + Objects.hashCode(this.total);
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
        final Order other = (Order) obj;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.stateCode, other.stateCode)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        if (!Objects.equals(this.productRate, other.productRate)) {
            return false;
        }
        if (!Objects.equals(this.productLabourRate, other.productLabourRate)) {
            return false;
        }
        if (!Objects.equals(this.stateTaxRate, other.stateTaxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.labourCost, other.labourCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }
    
    
    
}
