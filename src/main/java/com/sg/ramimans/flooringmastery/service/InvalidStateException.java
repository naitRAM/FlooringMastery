package com.sg.ramimans.flooringmastery.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 27, 2021
 * purpose: 
 */
public class InvalidStateException extends Exception {
    public InvalidStateException(String message) {
        super(message);
    }
    public InvalidStateException(String message, Throwable cause) {
        super(message);
    }
}
