package com.sg.ramimans.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Oct. 8, 2021
 * purpose: 
 */
public class AuditDaoFileImpl implements AuditDao{
    
    private final String FILE_NAME = "Audit/audit.txt";
    private PrintWriter output;
    private LocalDateTime timestamp;
    @Override
    public void writeAuditEntry(String entry) throws DaoException {
        try {
            this.output = new PrintWriter(new FileWriter(FILE_NAME, true));
        } catch (IOException e) {
            throw new DaoException("Could not write audit data");
    }
        this.timestamp = LocalDateTime.now();
        this.output.print("******************************");
        this.output.println("\n" + timestamp + entry);
        this.output.flush();
        this.output.close();
    }
    
}
