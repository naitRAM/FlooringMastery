package com.sg.ramimans.flooringmastery.model;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.stateCode);
        hash = 79 * hash + Objects.hashCode(this.stateName);
        hash = 79 * hash + Objects.hashCode(this.taxRate);
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
        final StateTax other = (StateTax) obj;
        if (!Objects.equals(this.stateCode, other.stateCode)) {
            return false;
        }
        if (!Objects.equals(this.stateName, other.stateName)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        return true;
    }
    
}
