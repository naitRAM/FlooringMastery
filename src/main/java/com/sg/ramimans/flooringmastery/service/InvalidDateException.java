package com.sg.ramimans.flooringmastery.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 27, 2021
 * purpose: 
 */
public class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
    public InvalidDateException(String message, Throwable cause) {
        super(message);
    }
}

