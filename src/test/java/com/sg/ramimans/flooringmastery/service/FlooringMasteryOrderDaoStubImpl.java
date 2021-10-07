package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.OrderDao;
import com.sg.ramimans.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Oct. 7, 2021
 * purpose: 
 */
public class FlooringMasteryOrderDaoStubImpl implements OrderDao {

    public Order onlyOrder ;
    
    public FlooringMasteryOrderDaoStubImpl() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("450.00");
        this.onlyOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
        productName, productRate, productLabourRate, area); 
    }
    
    public FlooringMasteryOrderDaoStubImpl(Order order) {
        this.onlyOrder = order;
    }
    @Override
    public Order getOrder(String orderId, LocalDate orderDate) throws DaoException {
        if (onlyOrder.getOrderId() == onlyOrder.getOrderId()) {
            return this.onlyOrder;
    } else {
            return null;
        }
    }

    @Override
    public Collection<Order> getAllOrders(LocalDate orderDate) throws DaoException {
        Map<String, Order> orders = new HashMap<>();
        orders.put(Integer.toString(onlyOrder.getOrderId()), onlyOrder);
        return orders.values();
    }

    @Override
    public Order addOrder(Order order, LocalDate orderDate) throws DaoException {
        if (onlyOrder.getOrderId() == order.getOrderId()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order, LocalDate orderDate) throws DaoException {
        if (onlyOrder.getOrderId() == order.getOrderId()) {
            return order;
        } else {
            return order;
        }
    }

    @Override
    public Order deleteOrder(String orderId, LocalDate orderDate) throws DaoException {
        if (orderId.equals(Integer.toString(onlyOrder.getOrderId()))) {
            return onlyOrder;
        } else {
            return null;
            
        }
    }

}
