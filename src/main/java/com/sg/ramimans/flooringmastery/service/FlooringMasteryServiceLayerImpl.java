package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.OrderDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.ProductDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.StateTaxDaoFileImpl;
import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 27, 2021 purpose:
 */
public class FlooringMasteryServiceLayerImpl {

    private final OrderDaoFileImpl orderDao;
    private final ProductDaoFileImpl productDao;
    private final StateTaxDaoFileImpl statesDao;

    public FlooringMasteryServiceLayerImpl(OrderDaoFileImpl orderDao, ProductDaoFileImpl productDao, StateTaxDaoFileImpl statesDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.statesDao = statesDao;
    }
    
    public void validateOrderDate(LocalDate date) throws InvalidDateException {
        if (!LocalDate.now().isBefore(date)) {
            throw new InvalidDateException("Order date not in future");
        }
    }
    
    public void validateCustomerName(Order customerOrder) throws InvalidCustomerNameException {
        if (! customerOrder.getCustomerName().matches("^[A-Za-z0-9.,]+$")) {
            throw new InvalidCustomerNameException("Customer name format is invalid");
        };
    }
    
    public void validateSquareFootage(Order customerOrder) throws InsufficientAreaException {
        if (customerOrder.getArea().compareTo(new BigDecimal("100")) < 0) {
            throw new InsufficientAreaException("Square footage must be 100 sq ft or more");
        };
    }
    public Order processNewOrder(Order order, LocalDate date) throws InvalidDateException, InsufficientAreaException, InvalidStateException, DaoException, InvalidProductException, InvalidCustomerNameException {
            this.validateOrderDate(date);
            this.validateCustomerName(order);
            this.validateSquareFootage(order);
        
        
        StateTax state = this.getState(order.getStateCode());
        Product product = this.getProduct(order.getProductName());
        Order newOrder = new Order(order.getCustomerName(), state, product, order.getArea());
        return orderDao.addOrder(order, date);

    }

    public StateTax getState(String stateCode) throws DaoException, InvalidStateException {
        StateTax state = statesDao.getState(stateCode);
        if (state == null) {
            throw new InvalidStateException("Orders cannot be processed for state " + stateCode);
        }
        return state;
    }

    public Product getProduct(String productName) throws DaoException, InvalidProductException {
        Product product = productDao.getProduct(productName);
        if (product == null) {
            throw new InvalidProductException(productName + " is not an available material");
        }
        return product;
    }

    public Collection<Product> getAllProducts() throws DaoException {
        return productDao.getAllProducts();
    }

    public Collection<StateTax> getAllStates() throws DaoException {
        return statesDao.getAllStates();
    }

    public Collection<Order> getAllOrders(LocalDate date) throws NoRecordsException {
        Collection<Order> orders;
        try {
            orders = orderDao.getAllOrders(date);
            if (orders.isEmpty()) {
                throw new NoRecordsException("Could not find orders for that date");
            }
        } catch (DaoException e) {
            throw new NoRecordsException("Could not find orders for that date", e);
        }
        return orders;

    }
    
    public Order getOrder(String orderId, LocalDate date) throws DaoException, OrderNotFoundException { 
        Order queryOrder = orderDao.getOrder(orderId, date);
        if (queryOrder == null) {
            throw new OrderNotFoundException("Could not find order with order ID " + orderId);
        }
        return queryOrder;
    }
    public Order editOrder(Order order, LocalDate date) throws DaoException, OrderNotFoundException, InvalidProductException, InvalidStateException, InsufficientAreaException, InvalidDateException {
        this.validateOrderDate(date);
        this.validateSquareFootage(order);
        Order orderToEdit = this.getOrder(Integer.toString(order.getOrderId()), date);
        Product newProduct = this.getProduct(order.getProductName());
        StateTax newState = this.getState(order.getStateCode());
        BigDecimal newArea = order.getArea().setScale(2, RoundingMode.HALF_UP);
        orderToEdit.setArea(newArea);
        orderToEdit.setNewState(newState);
        orderToEdit.setNewProduct(newProduct);
        orderToEdit.setCustomerName(order.getCustomerName());
        return orderDao.editOrder(orderToEdit, date);
    }
    
    public Order deleteOrder (Order order, LocalDate date) throws DaoException, OrderNotFoundException {
        Order orderToDelete = this.getOrder(Integer.toString(order.getOrderId()), date);
        Order deletedOrder = orderDao.deleteOrder(Integer.toString(order.getOrderId()), date);
        return deletedOrder;
    }
}
