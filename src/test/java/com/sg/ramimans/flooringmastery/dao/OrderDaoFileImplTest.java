/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Order;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rmans
 */
public class OrderDaoFileImplTest {
    
    OrderDao testDao;
    
    public OrderDaoFileImplTest() {
        
    }
    
    @BeforeEach
    public void setUp() {
       testDao = new OrderDaoFileImpl("Tests/OrderDao/Orders_");
    }
    
    @AfterEach
    public void tearDown() throws IOException {
        new FileWriter("Tests/OrderDao/Orders_" + 
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy")) 
                + ".txt");
    }
    
    @Test 
    public void testAddGetOrder() throws Exception {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal area = new BigDecimal("450.00").setScale(2, RoundingMode.HALF_UP);
        Order testOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        testDao.addOrder(testOrder, LocalDate.now());
        Order returnedOrder = testDao.getOrder(Integer.toString(orderId), LocalDate.now());
        
        assertEquals(testOrder, returnedOrder, "Checking all properties of Order returned." );
        
        
        
    }
    
    @Test
    public void testGetAllOrders() throws Exception {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal area = new BigDecimal("450.00");
        Order testOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        testDao.addOrder(testOrder, LocalDate.now());
        
        int orderId2 = 2;
        String customerName2 = "Marla Singer";
        String stateCode2 = "MA";
        BigDecimal stateTaxRate2 = new BigDecimal("7.00").setScale(2, RoundingMode.HALF_UP);
        String productName2 = "Laminate";
        BigDecimal productRate2 = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate2 = new BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal area2 = new BigDecimal("200.00").setScale(2, RoundingMode.HALF_UP);
        Order testOrder2 = new Order(orderId2, customerName2, stateCode2, stateTaxRate2,
                productName2, productRate2, productLabourRate2, area2);
        testDao.addOrder(testOrder2, LocalDate.now());
        
        Collection<Order> orders = testDao.getAllOrders(LocalDate.now());
        
        assertFalse(orders.isEmpty(), "Orders must not be empty");
        assertEquals(orders.size(), 2, "There must be two orders in Orders");
        
        assertTrue(orders.contains(testOrder), "Orders must contain testOrder1");
        assertTrue(orders.contains(testOrder2), "Orders must contain testOrder2");
        
    }
    
    @Test 
    public void testEditGetOrder() throws Exception {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal area = new BigDecimal("450.00").setScale(2, RoundingMode.HALF_UP);
        Order testOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        testDao.addOrder(testOrder, LocalDate.now());
        
        
        String newCustomerName = "Marla Singer";
        String newStateCode = "MA";
        BigDecimal newStateTaxRate = new BigDecimal("7.00").setScale(2, RoundingMode.HALF_UP);
        String newProductName = "Laminate";
        BigDecimal newProductRate = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal newProductLabourRate = new BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal newArea = new BigDecimal("200.00").setScale(2, RoundingMode.HALF_UP);
        Order updatedOrder = new Order(orderId, newCustomerName, newStateCode, newStateTaxRate,
                newProductName, newProductRate, newProductLabourRate, newArea);
        
        testDao.editOrder(updatedOrder, LocalDate.now());
        
        Order returnedOrder = testDao.getOrder(Integer.toString(orderId), LocalDate.now());
        
        assertEquals(testDao.getAllOrders(LocalDate.now()).size(), 1, "There must only be one order in orders");
        assertEquals(updatedOrder, returnedOrder, "Returned order properties must equal updated order");
    }
    
    @Test public void testAddDeleteOrder() throws Exception {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        BigDecimal area = new BigDecimal("450.00").setScale(2, RoundingMode.HALF_UP);
        Order testOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        testDao.addOrder(testOrder, LocalDate.now());
        
        Order deletedOrder = testDao.deleteOrder(Integer.toString(orderId), LocalDate.now());
        assertEquals(testOrder, deletedOrder, "All properties of added order must equal those of deleted order");
        
        assertTrue(testDao.getAllOrders(LocalDate.now()).isEmpty(), "Must have no orders remaining after deleting only order" );
        
        
    }
}
