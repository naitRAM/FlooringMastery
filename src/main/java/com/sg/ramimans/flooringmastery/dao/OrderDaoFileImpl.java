package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 26, 2021
 * purpose: 
 */
@Component
public class OrderDaoFileImpl implements OrderDao {
    public String inventoryFileFormat = "Orders/Orders_";
    public static final String DELIMITER = "::";
    public final Map<String, Order> orders = new HashMap<>();
    
    public OrderDaoFileImpl() {
        
    }
    
    public OrderDaoFileImpl(String FileFormat) {
        this.inventoryFileFormat = FileFormat;
    }
    
    private void loadOrders(LocalDate date) throws DaoException {
        Scanner fileInput;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String orderDate = date.format(formatter);
        this.orders.clear();
        try {
            fileInput = new Scanner(new BufferedReader(new FileReader(this.inventoryFileFormat + orderDate + ".txt")));
        } catch (FileNotFoundException e) {
            throw new DaoException("Could not find data file for date " + orderDate, e);
        }
        
        String currentLine;
        Order currentOrder;
        while (fileInput.hasNextLine()) {
            currentLine = fileInput.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            this.orders.put(Integer.toString(currentOrder.getOrderId()), currentOrder);
        }
    }
    
    private Order unmarshallOrder (String entry) {
        String[] entryArray = entry.split(DELIMITER);
        int orderNumber = Integer.parseInt(entryArray[0]);
        String customerName = entryArray[1];
        String state = entryArray[2];
        BigDecimal taxRate = new BigDecimal(entryArray[3]).setScale(2, RoundingMode.HALF_UP);
        String productType = entryArray[4];
        BigDecimal costPerSquareFoot = new BigDecimal(entryArray[6]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourCostPerSquareFoot = new BigDecimal(entryArray[7]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal area = new BigDecimal(entryArray[5]).setScale(2, RoundingMode.HALF_UP);
        return new Order(orderNumber, customerName, state, taxRate, productType, costPerSquareFoot, labourCostPerSquareFoot, area);
    }
    
    
    
    private void writeOrders(LocalDate date) throws DaoException {
        PrintWriter fileOutput;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String orderDate = date.format(formatter);
        try {
            fileOutput = new PrintWriter(new FileWriter(this.inventoryFileFormat + orderDate + ".txt"));
        } catch (IOException e) {
            throw new DaoException("Error occured while writing to file", e);
        }
        String entry;
        Set<String> orderIds = this.orders.keySet();
        for (String orderId : orderIds) {
            entry = marshallOrder(orders.get(orderId));
            fileOutput.println(entry);
            fileOutput.flush();
        }
        fileOutput.close();
    }
    
    private String marshallOrder(Order order) {
        String entry = "";
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
    
    @Override
    public Order getOrder(String Id, LocalDate date) throws DaoException{
        this.loadOrders(date);
        return this.orders.get(Id);
    }
    
    @Override
    public Collection<Order> getAllOrders(LocalDate orderDate) throws DaoException {
        this.loadOrders(orderDate);
        return this.orders.values();
    }
    
    @Override
    public Order addOrder(Order order, LocalDate date) throws DaoException{
        try {
            this.getAllOrders(date);
        } catch (DaoException e) {
            this.orders.clear();
        }
        Set<String> stringIds = this.orders.keySet();
        int orderId = 1;
        for (String id : stringIds) {
            int numId = Integer.parseInt(id);
            if ( numId >= orderId) {
                orderId = numId +1 ;
            }
        }
        order.setOrderId(orderId);
        this.orders.put(Integer.toString(orderId), order);
        this.writeOrders(date);
        return order;
    }
    
    @Override
    public Order deleteOrder(String Id, LocalDate date) throws DaoException {
        this.loadOrders(date);
        Order deletedOrder = this.orders.remove(Id);
        this.writeOrders(date);
        return deletedOrder;
    }
    
    @Override
    public Order editOrder(Order order, LocalDate date) throws DaoException {
        this.loadOrders(date);
        String orderId = Integer.toString(order.getOrderId());
        Order orderToEdit = this.orders.get(orderId);
        if (orderToEdit != null) {
            this.orders.put(orderId, order);
        }
        this.writeOrders(date);
        return this.orders.get(orderId);
    }
    
    
}
