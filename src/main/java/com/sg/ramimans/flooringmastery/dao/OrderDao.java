/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Order;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author rmans
 */
public interface OrderDao {
    public Order getOrder(String orderId, LocalDate orderDate) throws DaoException;
    public Collection<Order> getAllOrders(LocalDate orderDate) throws DaoException;
    public Order addOrder(Order order, LocalDate orderDate) throws DaoException;
    public Order editOrder(Order order, LocalDate orderDate) throws DaoException;
    public Order deleteOrder(String orderId, LocalDate orderDate) throws DaoException;
}
