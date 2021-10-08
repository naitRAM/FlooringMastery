package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.OrderDao;
import com.sg.ramimans.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Oct. 7, 2021
 * purpose: 
 */
@Component
public class OrderDaoStubImpl implements OrderDao {

    
    public Order onlyOrder ;
    
    @Autowired
    public OrderDaoStubImpl() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal area = new BigDecimal("450.00").setScale(2, RoundingMode.HALF_UP);
        this.onlyOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
        productName, productRate, productLabourRate, area); 
    }
    
    
    public OrderDaoStubImpl(Order order) {
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
            return onlyOrder;
    }

    @Override
    public Order editOrder(Order order, LocalDate orderDate) throws DaoException {
        if (onlyOrder.getOrderId() == order.getOrderId()) {
            return order;
        } else {
            return null;
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
