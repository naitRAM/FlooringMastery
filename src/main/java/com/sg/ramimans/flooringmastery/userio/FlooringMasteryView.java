package com.sg.ramimans.flooringmastery.userio;

import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Oct. 2, 2021 purpose:
 */
@Component
public class FlooringMasteryView {

    UserIO io;
    
    @Autowired
    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int displayMenuAndGetSelection() {
        io.print("");
        io.print("Flooring Program");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");
        io.print("");
        int userSelection = io.readInt("Choose a menu item (1-6):", 1, 6);

        return userSelection;
    }

    public void displayAllOrders(Collection<Order> orders) {
        io.print("");
        for (Order order : orders) {

            io.print(order.getOrderId() + " " + order.getCustomerName() + " " + order.getTotal());
        }
    }

    public void displayAvailableProducts(Collection<Product> products) {
        io.print("");
        io.print("Available products include: ");
        for (Product product : products) {
            io.print(product.getProduct() + "   " + "$" + product.getCost() + "/sq ft" + "   " + "$" + product.getLabour() + "/sq ft labour");
        }
    }

    public void displayAvailableStates(Collection<StateTax> states) {
        io.print("");
        io.print("We ship to the following states: ");
        for (StateTax state : states) {
            io.print(state.getStateName() + " (" + state.getStateCode() + ")");
        }
    }

    public LocalDate getDate() {
        io.print("");
        return io.readLocalDate("Enter a  date in YYYY-MM-DD format: ");
    }

    public LocalDate getFutureDate() {
        io.print("");
        return io.readFutureLocalDate("Enter order date in YYYY-MM-DD format (must be in future):");
    }

    private boolean printOrderInfoAndConfirm(Order order, LocalDate date, String message) {
        
        io.print("Customer name: " + order.getCustomerName());
        io.print("Order date: " + date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        io.print("Order State: " + order.getStateCode());
        io.print("Order Product: " + order.getProductName());
        io.print("Order Dimensions: " + order.getArea() + " sq ft");
        io.print("Product cost: $" + order.getMaterialCost() + "  ($" + order.getProductRate() + " / sq ft)");
        io.print("Labour cost: $" + order.getLabourCost() + "  ($" + order.getProductLabourRate() + " / sq ft)");
        io.print("Net Total: $" + order.getMaterialCost().add(order.getLabourCost()));
        io.print("Tax: $" + order.getTax() + "  (" + order.getStateCode() + " @ " + order.getStateTaxRate() + "%)");
        io.print("Total: $" + order.getTotal());
        io.print("");
        String userResponse = io.readString(message + " (Y/N)");
        if (userResponse.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }
    
    public void printMessage(String message) {
        io.print("");
        io.print(message);
    }
  
    public boolean confirmAddOrder(Order order, LocalDate date) {
        String confirmationMessage = "Are you sure you want to add this order?";
        io.print("");
        io.print("NEW ORDER");
        io.print("");
        return this.printOrderInfoAndConfirm(order, date, confirmationMessage);
    }

    public boolean confirmEditOrder(Order order, LocalDate date) {
        String confirmationMessage = "Are you sure you want to edit order #" + order.getOrderId() + "?";
        io.print("");
        io.print("EDITED ORDER DETAILS");
        io.print("");
        io.print("Order ID: " + order.getOrderId());
        return this.printOrderInfoAndConfirm(order, date, confirmationMessage);
    }

    public boolean confirmDeleteOrder(Order order, LocalDate date) {
        String confirmationMessage = "Are you sure you want to delete order #" + order.getOrderId() + "?";
        io.print("");
        io.print("ORDER TO DELETE ");
        io.print("");
        io.print("Order ID: " + order.getOrderId());
        return this.printOrderInfoAndConfirm(order, date, confirmationMessage);
    }

    public Order getNewOrder(Collection<Product> products, Collection<StateTax> states) {
        io.print("");
        String customerName = io.readString("Enter customer name:");

        
        this.displayAvailableStates(states);

        io.print("");
        StateTax orderState = null;
        while (orderState == null) {
            String stateCode = io.readString("Enter a valid state code:");
            orderState = this.getState(states, stateCode);
        }

        
        this.displayAvailableProducts(products);

        io.print("");
        Product orderProduct = null;
        while (orderProduct == null) {
            String productName = io.readString("Enter a valid product: ");
            orderProduct = this.getProduct(products, productName);
        }

        io.print("");
        BigDecimal area = io.readBigDecimalGreaterOrEqual("Enter area (minimum of 100 sq ft): ", new BigDecimal("100"));
        Order newOrder = new Order(customerName, orderState, orderProduct, area);
        return newOrder;
    }

    public Order getEditedOrder(Collection<Order> orders, Collection<StateTax> states, Collection<Product> products) {

        this.displayAllOrders(orders);
        io.print("");
        Order validOrder = null;
        while (validOrder == null) {
            int orderId = io.readInt("Enter valid order number to edit details: ");
            validOrder = this.getOrder(orders, orderId);
        }
        io.print("");
        io.print("Customer name: " + validOrder.getCustomerName());
        String customerName = io.readString("Updated customer name (Hit Enter to keep old entry): ");
        if (customerName.isBlank()) {
            customerName = validOrder.getCustomerName();
        }
        
        io.print("");
        io.print("Order state code: " + validOrder.getStateCode());
        StateTax state = null;
        this.displayAvailableStates(states);
        while (state == null) {
            String editedStateCode = io.readString("Updated order state code (e.g. NY) (Hit Enter to keep old entry): ");
            if (editedStateCode.isBlank()) {
                state = this.getState(states, validOrder.getStateCode());
            } else {
                state = this.getState(states, editedStateCode);
            }
        }
        
        io.print("");
        io.print("Order product type: " + validOrder.getProductName());
        Product product = null;
        this.displayAvailableProducts(products);
        while (product == null) {
            String editedProductType = io.readString("Updated order product (Hit Enter to keep old entry): ");
            if (editedProductType.isBlank()) {
                product = this.getProduct(products, validOrder.getProductName());
            } else {
                product = this.getProduct(products, editedProductType);
            }
        }

        io.print("");
        io.print("Order area in sq ft: " + validOrder.getArea());
        BigDecimal area = null;
        while (area == null) {
            String areaString = io.readString("Enter updated area greater than 100 sq ft (Hit Enter to keep old entry: ");
            if (areaString.isBlank()) {
                area = validOrder.getArea();
            } else {
                try {
                    area = new BigDecimal(areaString).setScale(2, RoundingMode.HALF_UP);
                    if (area.compareTo(new BigDecimal("100")) < 0) {
                        io.print(area + " is less than 100");
                        area = null;
                    }
                } catch (NumberFormatException e) {
                    io.print("Invalid input");
                }
            }
        }

        validOrder.setCustomerName(customerName);
        validOrder.setNewState(state);
        validOrder.setNewProduct(product);
        validOrder.setArea(area);

        return validOrder;
    }

    public Order getOrderToDelete(Collection<Order> orders) {
        this.displayAllOrders(orders);
        Order validOrder = null;
        io.print("");
        while (validOrder == null) {
            int orderId = io.readInt("Enter valid order number to delete: ");
            validOrder = this.getOrder(orders, orderId);
        }
        return validOrder;
    }

    private StateTax getState(Collection<StateTax> states, String stateCode) {
        StateTax result = null;
        for (StateTax state : states) {
            if (state.getStateCode().equals(stateCode)) {
                result = state;
                return result;
            }
        }
        return result;
    }

    private Product getProduct(Collection<Product> products, String productName) {
        Product result = null;
        for (Product product : products) {
            if (product.getProduct().equals(productName)) {
                result = product;
                return result;
            }
        }
        return result;
    }

    private Order getOrder(Collection<Order> orders, int orderId) {
        Order result = null;
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                result = order;
                return result;
            }
        }
        return result;
    }
    
    
}
