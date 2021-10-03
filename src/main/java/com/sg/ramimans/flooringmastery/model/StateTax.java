package com.sg.ramimans.flooringmastery.model;

import java.math.BigDecimal;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 26, 2021
 * purpose: 
 */
public class StateTax {
    String stateCode;
    String stateName;
    BigDecimal taxRate;
    
    public StateTax(String stateCode, String stateName, BigDecimal taxRate) {
        this.stateCode = stateCode;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public String getStateName() {
        return this.stateName;
    }

    public BigDecimal getTaxRate() {
        return this.taxRate;
    }
}
