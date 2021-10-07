/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.StateTax;
import java.util.Collection;

/**
 *
 * @author rmans
 */
public interface StateTaxDao {
    public StateTax getState(String stateCode) throws DaoException;
    public Collection<StateTax> getAllStates() throws DaoException;
}
