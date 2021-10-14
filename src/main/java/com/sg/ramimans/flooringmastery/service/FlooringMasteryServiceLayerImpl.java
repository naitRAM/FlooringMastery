package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.AuditDao;
import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.OrderDao;
import com.sg.ramimans.flooringmastery.dao.OrderDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.ProductDao;
import com.sg.ramimans.flooringmastery.dao.ProductDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.StateTaxDao;
import com.sg.ramimans.flooringmastery.dao.StateTaxDaoFileImpl;
import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 27, 2021 purpose:
 */
@Component
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final StateTaxDao statesDao;
    private final AuditDao auditDao;
    
    @Autowired
    public FlooringMasteryServiceLayerImpl(OrderDao orderDao, ProductDao productDao, StateTaxDao statesDao, AuditDao auditDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.statesDao = statesDao;
        this.auditDao = auditDao;
    }

    private void validateOrderDate(LocalDate date) throws InvalidDateException {
        if (!LocalDate.now().isBefore(date)) {
            throw new InvalidDateException("Order date not in future");
        }
    }

    private void validateCustomerName(Order customerOrder) throws InvalidCustomerNameException {
        String name = customerOrder.getCustomerName();
        if (!name.matches("^[A-Za-z0-9., ]+$") || name.isBlank()) {
            throw new InvalidCustomerNameException("Customer name format is invalid");
        };
    }

    private void validateSquareFootage(Order customerOrder) throws InsufficientAreaException {
        if (customerOrder.getArea().compareTo(new BigDecimal("100")) < 0) {
            throw new InsufficientAreaException("Square footage must be 100 sq ft or more");
        };
    
    }
    
    @Override
    public Order processNewOrder(Order order, LocalDate date) throws InvalidDateException, InsufficientAreaException, InvalidStateException, DaoException, InvalidProductException, InvalidCustomerNameException {
        this.validateOrderDate(date);
        this.validateCustomerName(order);
        this.validateSquareFootage(order);

        StateTax state = this.getState(order.getStateCode());
        Product product = this.getProduct(order.getProductName());
        Order newOrder = new Order(order.getCustomerName(), state, product, order.getArea());
        Order processedOrder = orderDao.addOrder(order, date);
        auditDao.writeAuditEntry(this.createAuditEntry(processedOrder, date, "Order Add"));
        return processedOrder;
    }

    @Override
    public Order editOrder(Order order, LocalDate date) throws DaoException, OrderNotFoundException, InvalidProductException, InvalidStateException, InsufficientAreaException, InvalidDateException, InvalidCustomerNameException {
        this.validateOrderDate(date);
        this.validateSquareFootage(order);
        this.validateCustomerName(order);
        Order orderToEdit = this.getOrder(Integer.toString(order.getOrderId()), date);
        Product newProduct = this.getProduct(order.getProductName());
        StateTax newState = this.getState(order.getStateCode());
        BigDecimal newArea = order.getArea().setScale(2, RoundingMode.HALF_UP);
        orderToEdit.setArea(newArea);
        orderToEdit.setNewState(newState);
        orderToEdit.setNewProduct(newProduct);
        orderToEdit.setCustomerName(order.getCustomerName());
        
        
        Order editedOrder = orderDao.editOrder(orderToEdit, date);
        auditDao.writeAuditEntry(this.createAuditEntry(editedOrder, date, "Edit Order"));
        return editedOrder;
    }
    
    @Override
    public Order deleteOrder(Order order, LocalDate date) throws DaoException, OrderNotFoundException {
        Order orderToDelete = this.getOrder(Integer.toString(order.getOrderId()), date);
        Order deletedOrder = orderDao.deleteOrder(Integer.toString(order.getOrderId()), date);
        if (deletedOrder == null) {
            throw new OrderNotFoundException("Could not find order with order ID "  + order.getOrderId());
        }
        auditDao.writeAuditEntry(this.createAuditEntry(deletedOrder, date, "Delete Order"));
        return deletedOrder;
    }
    
    @Override
    public Order getOrder(String orderId, LocalDate date) throws DaoException, OrderNotFoundException {
        Order queryOrder = orderDao.getOrder(orderId, date);
        if (queryOrder == null) {
            throw new OrderNotFoundException("Could not find order with order ID " + orderId);
        }
        return queryOrder;
    }
    
    @Override
    public Product getProduct(String productName) throws DaoException, InvalidProductException {
        Product product = productDao.getProduct(productName);
        if (product == null) {
            throw new InvalidProductException(productName + " is not an available material");
        }
        return product;
    }
    
    @Override
    public StateTax getState(String stateCode) throws DaoException, InvalidStateException {
        StateTax state = statesDao.getState(stateCode);
        if (state == null) {
            throw new InvalidStateException("Orders cannot be processed for state " + stateCode);
        }
        return state;
    }
    
    @Override
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
    
    @Override
    public Collection<Product> getAllProducts() throws DaoException {
        return productDao.getAllProducts();
    }
    
    @Override
    public Collection<StateTax> getAllStates() throws DaoException {
        return statesDao.getAllStates();
    }
    
    private String createAuditEntry(Order order, LocalDate date, String message) {
        String DELIMITER = ",";
        String entry = "\n" + message + "\n";
        entry += "Order Date: " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyy")) + "\n";
        entry += order.getOrderId() + DELIMITER;
        entry += order.getCustomerName() + DELIMITER;
        entry += order.getStateCode() + DELIMITER;
        entry += order.getStateTaxRate() + DELIMITER;
        entry += order.getProductName() + DELIMITER;
        entry += order.getArea() + DELIMITER;
        entry += order.getProductRate() + DELIMITER;
        entry += order.getProductLabourRate() + DELIMITER;
        entry += order.getMaterialCost() + DELIMITER;
        entry += order.getLabourCost() + DELIMITER;
        entry += order.getTax() + DELIMITER;
        entry += order.getTotal();
        return entry;
    }
    
    public String exportOrders() throws DaoException {
      return orderDao.exportOrders();
    }

}
