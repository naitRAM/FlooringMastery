/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rmans
 */
public class StateTaxDaoFileImplTest {
    
    StateTaxDao testDao;
    
    public StateTaxDaoFileImplTest() {
    }
        
    @BeforeEach
    public void setUp() {
        testDao = new StateTaxDaoFileImpl();
    }
    
    @Test
    public void testGetProducts() throws Exception {
        Collection<StateTax> states = testDao.getAllStates();
        for (StateTax state : states) {
            StateTax returnedState = testDao.getState(state.getStateCode());
            assertEquals(state, returnedState, "State must have all properties equal to returnedState");
        }
    }
}
