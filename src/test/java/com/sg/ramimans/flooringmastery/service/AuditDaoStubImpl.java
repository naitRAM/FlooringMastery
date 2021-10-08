package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.AuditDao;
import com.sg.ramimans.flooringmastery.dao.DaoException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Oct. 8, 2021
 * purpose: 
 */
@Component
public class AuditDaoStubImpl implements AuditDao {

    @Override
    public void writeAuditEntry(String entry) throws DaoException {
        
    }

}
