package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.StateTaxDao;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Oct. 7, 2021
 * purpose: 
 */
@Component
public class StateTaxDaoStubImpl implements StateTaxDao{
    Map<String, StateTax> states = new HashMap<>();
    
    @Autowired
    public StateTaxDaoStubImpl() {
        String firstStateCode = "GA";
        String firstStateName = "Georgia";
        BigDecimal firstStateTax = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        states.put(firstStateCode, new StateTax(firstStateCode, firstStateName, firstStateTax));
        String secondStateCode = "MA";
        String secondStateName = "Massachusetts";
        BigDecimal secondStateTax = new BigDecimal("7.00").setScale(2, RoundingMode.HALF_UP);
        states.put(secondStateCode, new StateTax(secondStateCode, secondStateName, secondStateTax));
    }
    
    
    @Override
    public StateTax getState(String stateCode) throws DaoException {
        return states.get(stateCode);
    }

    @Override
    public Collection<StateTax> getAllStates() throws DaoException {
        return states.values();
    }

}
