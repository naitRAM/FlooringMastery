/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author rmans
 */
public interface FlooringMasteryService {
    public Order processNewOrder(Order order, LocalDate date) throws InvalidDateException, 
            InsufficientAreaException, InvalidStateException, DaoException, 
            InvalidProductException, InvalidCustomerNameException;
    
    public Order editOrder(Order order, LocalDate date) throws DaoException, OrderNotFoundException, 
            InvalidProductException, InvalidStateException, InsufficientAreaException, 
            InvalidDateException, InvalidCustomerNameException;
    
    public Order deleteOrder(Order order, LocalDate date) throws DaoException, OrderNotFoundException;
    
    public Order getOrder(String orderId, LocalDate date) throws DaoException, OrderNotFoundException;
    
    public Product getProduct(String productName) throws DaoException, InvalidProductException;

    public StateTax getState(String stateCode) throws DaoException, InvalidStateException;

    public Collection<Order> getAllOrders(LocalDate date) throws NoRecordsException;
    
    public Collection<Product> getAllProducts() throws DaoException;
    
    public Collection<StateTax> getAllStates() throws DaoException;
    
}
