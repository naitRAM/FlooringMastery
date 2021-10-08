/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.AuditDao;
import com.sg.ramimans.flooringmastery.dao.AuditDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.OrderDao;
import com.sg.ramimans.flooringmastery.dao.ProductDao;
import com.sg.ramimans.flooringmastery.dao.StateTaxDao;
import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rmans
 */
public class FlooringMasteryServiceLayerImplTest {

    FlooringMasteryService service;

    public FlooringMasteryServiceLayerImplTest() {
        OrderDao orderDao = new OrderDaoStubImpl();
        ProductDao productDao = new ProductDaoStubImpl();
        StateTaxDao statesDao = new StateTaxDaoStubImpl();
        AuditDao auditDao = new AuditDaoStubImpl();

        this.service = new FlooringMasteryServiceLayerImpl(orderDao, productDao, statesDao, auditDao);
    }

    @Test
    public void testNewOrderValid() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("450.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);

        LocalDate date = LocalDate.now().plusDays(1);
        try {
            service.processNewOrder(newOrder, date);
        } catch (InvalidDateException | InsufficientAreaException
                | InvalidStateException | DaoException
                | InvalidProductException | InvalidCustomerNameException e) {
            fail("Valid order should not throw any exceptions");
        }

    }

    @Test
    public void testNewOrderInvalidDate() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("450.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now();

        try {
            service.processNewOrder(newOrder, date);
            fail("No exception was thrown for new order with invalid date");
        } catch (InsufficientAreaException | InvalidStateException | DaoException
                | InvalidProductException | InvalidCustomerNameException e) {
            fail("Incorrect exception was thrown for new order with invalid date");
        } catch (InvalidDateException e) {
            return;
        }
    }

    @Test
    public void testNewOrderInvalidArea() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("99.99");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);

        try {
            service.processNewOrder(newOrder, date);
            fail("No exception was thrown for new order with invalid date");
        } catch (InvalidDateException | InvalidStateException | DaoException
                | InvalidProductException | InvalidCustomerNameException e) {
            fail("Incorrect exception was thrown for new order with invalid date");
        } catch (InsufficientAreaException e) {
            return;
        }

    }

    @Test
    public void testNewOrderInvalidState() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "FL";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("100.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);

        try {
            service.processNewOrder(newOrder, date);
            fail("No exception was thrown for new order with invalid date");
        } catch (InvalidDateException | InsufficientAreaException | DaoException
                | InvalidProductException | InvalidCustomerNameException e) {
            fail("Incorrect exception was thrown for new order with invalid date");
        } catch (InvalidStateException e) {
            return;
        }

    }

    @Test
    public void testNewOrderInvalidProduct() {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Wood";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("100.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);

        try {
            service.processNewOrder(newOrder, date);
            fail("No exception was thrown for new order with invalid date");
        } catch (InvalidDateException | InsufficientAreaException | DaoException
                | InvalidStateException | InvalidCustomerNameException e) {
            fail("Incorrect exception was thrown for new order with invalid date");
        } catch (InvalidProductException e) {
            return;
        }
    }

    @Test
    public void testNewOrderInvalidCustomerName() {
        int orderId = 1;
        String customerName = "Robert: Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("100.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);

        try {
            service.processNewOrder(newOrder, date);
            fail("No exception was thrown for new order with invalid date");
        } catch (InvalidDateException | InsufficientAreaException | DaoException
                | InvalidStateException | InvalidProductException e) {
            fail("Incorrect exception was thrown for new order with invalid date");
        } catch (InvalidCustomerNameException e) {
            return;
        }
    }

    @Test
    public void testGetValidEditOrder() {
        int orderId = 1;
        String customerName = "Marla Singer";
        String stateCode = "MA";
        BigDecimal stateTaxRate = new BigDecimal("7.00");
        String productName = "Laminate";
        BigDecimal productRate = new BigDecimal("4.00");
        BigDecimal productLabourRate = new BigDecimal("2.50");
        BigDecimal area = new BigDecimal("400.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);

        try {
            Order oldOrder = service.getOrder(Integer.toString(orderId), date);
            Order editedOrder = service.editOrder(newOrder, date);
            assertEquals(editedOrder, newOrder, "Returned order must be equal to edited order");
        } catch (DaoException | OrderNotFoundException | InvalidProductException
                | InvalidStateException | InsufficientAreaException
                | InvalidDateException | InvalidCustomerNameException e) {
            fail("Edited order is valid, no exception should have been thrown");
        }
    }

    @Test
    public void testEditInvalidOrderNumber() {
        int orderId = 2;
        String customerName = "Marla Singer";
        String stateCode = "MA";
        BigDecimal stateTaxRate = new BigDecimal("7.00");
        String productName = "Laminate";
        BigDecimal productRate = new BigDecimal("4.00");
        BigDecimal productLabourRate = new BigDecimal("2.50");
        BigDecimal area = new BigDecimal("400.00");
        Order newOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);
        try {
            service.editOrder(newOrder, date);
        } catch (DaoException | InvalidProductException
                | InvalidStateException | InsufficientAreaException
                | InvalidDateException | InvalidCustomerNameException e) {
            fail("Incorrect exception thrown while editing order with non-existent id");
        } catch (OrderNotFoundException e) {
            return;
        }
    }

    @Test
    public void testDeleteOrder() throws Exception {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("450.00");
        Order order = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);
        
        Order deletedOrder = service.deleteOrder(order, date);
        assertNotNull(deletedOrder, "Deleted order should not be null");
        assertEquals(deletedOrder, order, "Order should be equal to deleted order");
       
    }
    
    @Test
    public void testDeleteInvalidOrder() {
        
        int orderId = 2;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("450.00");
        Order invalidOrder = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        LocalDate date = LocalDate.now().plusDays(1);
    
        try {
            Order deletedOrder = service.deleteOrder(invalidOrder, date);
            fail("Deleting order with non-existent order Id should throw exception");
        } catch (DaoException e) {
            fail("Incorrect exception was thrown for deleting invalid order");
        } catch (OrderNotFoundException e) {
            return;
        }

    }
    
    @Test
    public void testGetAllOrders() throws Exception {
        int orderId = 1;
        String customerName = "Robert Paulson";
        String stateCode = "GA";
        BigDecimal stateTaxRate = new BigDecimal("5.00");
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50");
        BigDecimal productLabourRate = new BigDecimal("0.25");
        BigDecimal area = new BigDecimal("450.00");
        Order order = new Order(orderId, customerName, stateCode, stateTaxRate,
                productName, productRate, productLabourRate, area);
        
       
        
        LocalDate date = LocalDate.now().plusDays(1);
        Collection<Order> returnedOrders = service.getAllOrders(date);
        
        assertFalse(returnedOrders.isEmpty(), "Returned orders must not be empty");
        assertEquals(returnedOrders.size(), 1, "Must have one orders in returned orders");
        assertTrue(returnedOrders.contains(order), "Orders must containorder");
        
        
    }

    @Test
    public void testGetAllProducts() throws Exception{
        String firstProductName = "Straw";
        BigDecimal firstProductRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstProductLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        Product firstProduct = new Product(firstProductName, firstProductRate, firstProductLabourRate);
        
        String secondProductName = "Laminate";
        BigDecimal secondProductRate = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondProductLabourRate = new BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP);
        Product secondProduct = new Product(firstProductName, firstProductRate, firstProductLabourRate);
        
        
        Collection<Product> returnedProducts = service.getAllProducts();
        assertFalse(returnedProducts.isEmpty(), "Returned productss must not be empty");
        assertEquals(returnedProducts.size(), 2, "Must have two products in returned products");
        assertTrue(returnedProducts.contains(firstProduct), "Products must contain first product");
        assertTrue(returnedProducts.contains(secondProduct), "Products must contain second product");
    }
            
    @Test
    public void testGetAllStates() throws Exception {
        String firstStateCode = "GA";
        String firstStateName = "Georgia";
        BigDecimal firstStateTax = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        StateTax firstState = new StateTax(firstStateCode, firstStateName, firstStateTax);
        String secondStateCode = "MA";
        String secondStateName = "Massachusetts";
        BigDecimal secondStateTax = new BigDecimal("7.00").setScale(2, RoundingMode.HALF_UP);
        StateTax secondState = new StateTax(secondStateCode, secondStateName, secondStateTax);
        
        Collection<StateTax> returnedStates = service.getAllStates();
        
        assertFalse(returnedStates.isEmpty(), "Returned states must not be empty");
        assertEquals(returnedStates.size(), 2, "Must have two states in returned states");
        assertTrue(returnedStates.contains(firstState), "States must contain first state");
        assertTrue(returnedStates.contains(secondState), "States must contain second state");
    }
    
    @Test
    public void testGetState() throws Exception{
        String stateCode = "GA";
        String stateName = "Georgia";
        BigDecimal stateTax = new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP);
        StateTax createdState = new StateTax(stateCode, stateName, stateTax);
        
        StateTax state = service.getState(stateCode);
        
        assertNotNull(state, "Returned state must not be null");
        assertEquals(state, createdState, "Created state must equal state returned");
        
    }

    @Test
    public void testGetInvalidState() {
        String stateCode = "FL";
        String stateName = "Florida";
        BigDecimal stateTax = new BigDecimal("4.75").setScale(2, RoundingMode.HALF_UP);
        StateTax createdState = new StateTax(stateCode, stateName, stateTax);
        
        try {
        StateTax state = service.getState(stateCode);
        fail("Exception should have been thrown for invalid state");
        } catch (DaoException e) {
            fail("Incorrect exception thrown for invalid state");
        } catch (InvalidStateException e) {
            return;
        }
        
        
    }
    
    @Test
    public void testGetProduct() throws Exception{
        String productName = "Straw";
        BigDecimal productRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        Product createdProduct = new Product(productName, productRate, productLabourRate);
        
        Product product = service.getProduct(productName);
        
        assertNotNull(product, "Returned product must not be null");
        assertEquals(product, createdProduct, "Created product must equal product returned");
        
    }
    
    @Test
    public void testGetInvalidProduct(){
        String productName = "Wood";
        BigDecimal productRate = new BigDecimal("7.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal productLabourRate = new BigDecimal("4.50").setScale(2, RoundingMode.HALF_UP);
        Product createdProduct = new Product(productName, productRate, productLabourRate);
        
        try {
        Product product = service.getProduct(productName);
        fail("Exception should have been thrown for invalid product name");
        } catch (DaoException e) {
            fail("Incorrect exception thrown");
        } catch (InvalidProductException e) {
            return;
        }
        
    }
}
