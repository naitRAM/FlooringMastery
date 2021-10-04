package com.sg.ramimans.flooringmastery.service;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Oct. 3, 2021
 * purpose: 
 */
public class InvalidCustomerNameException extends Exception {
    public InvalidCustomerNameException(String message) {
        super(message);
    }
    public InvalidCustomerNameException(String message, Throwable cause) {
        super(message);
    }
}
