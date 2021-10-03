package com.sg.ramimans.flooringmastery.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 27, 2021
 * purpose: 
 */
public class InsufficientAreaException extends Exception {
    public InsufficientAreaException(String message) {
        super(message);
    }
    public InsufficientAreaException(String message, Throwable cause) {
        super(message);
    }
}
