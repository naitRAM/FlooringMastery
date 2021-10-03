package com.sg.ramimans.flooringmastery.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 29, 2021
 * purpose: 
 */
public class NoRecordsException extends Exception {
    public NoRecordsException(String message) {
        super(message);
    }
    public NoRecordsException(String message, Throwable cause) {
        super(message);
    }
}
