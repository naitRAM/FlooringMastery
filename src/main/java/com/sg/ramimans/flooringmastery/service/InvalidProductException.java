package com.sg.ramimans.flooringmastery.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 27, 2021
 * purpose: 
 */
public class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
    public InvalidProductException(String message, Throwable cause) {
        super(message);
    }
}
