package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.StateTax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 27, 2021
 * purpose: 
 */
public class StateTaxDaoFileImpl {
    private String DELIMITER = ",";
    private String fileName;
    private Map<String, StateTax> states = new HashMap<>() ;
    
    public StateTaxDaoFileImpl() {
        this.fileName = "Data/Taxes.txt";
        
    }
    
    public StateTaxDaoFileImpl(String fileName) {
        this.fileName = fileName;
    }
    
    private void loadStates() throws DaoException{
        Scanner fileInput;
        try {
            fileInput = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new DaoException("Could not read State tax data", e);
        }
        
        StateTax state;
        String currentLine;
        
        while (fileInput.hasNextLine()) {
            currentLine = fileInput.nextLine();
            state = unmarshallStateTax(currentLine);
            this.states.put(state.getStateCode(), state);
        }
    }
    
    private StateTax unmarshallStateTax(String entry) {
        String[] entryArray = entry.split(DELIMITER);
        String stateCode = entryArray[0];
        String stateName = entryArray[1];
        BigDecimal tax = new BigDecimal(entryArray[2]).setScale(2, RoundingMode.HALF_UP);
        return new StateTax(stateCode, stateName, tax);
    }
    
    public StateTax getState(String stateCode) throws DaoException {
        this.loadStates();
        return this.states.get(stateCode);
    }
    
    public Collection<StateTax> getAllStates() throws DaoException{
        this.loadStates();
        return this.states.values();
    }
}
