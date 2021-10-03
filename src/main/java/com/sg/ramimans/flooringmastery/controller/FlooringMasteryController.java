package com.sg.ramimans.flooringmastery.controller;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.sg.ramimans.flooringmastery.service.InsufficientAreaException;
import com.sg.ramimans.flooringmastery.service.InvalidDateException;
import com.sg.ramimans.flooringmastery.service.InvalidProductException;
import com.sg.ramimans.flooringmastery.service.InvalidStateException;
import com.sg.ramimans.flooringmastery.service.NoRecordsException;
import com.sg.ramimans.flooringmastery.service.OrderNotFoundException;
import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import com.sg.ramimans.flooringmastery.userio.FlooringMasteryView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 29, 2021 purpose:
 */
public class FlooringMasteryController {

    private FlooringMasteryServiceLayerImpl service;
    private final FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryServiceLayerImpl service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() throws DaoException {

        boolean usingApp = true;
        Scanner userInput = new Scanner(System.in);
        Collection<Product> products = service.getAllProducts();
        Collection<StateTax> states = service.getAllStates();
        while (usingApp) {
            int userSelection = view.displayMenuAndGetSelection();
            switch (userSelection) {
                case 1:
                    try {
                    view.displayAllOrders(service.getAllOrders(view.getDate()));
                } catch (NoRecordsException e) {
                    System.out.println(e.getMessage());
                }
                break;
                case 2:
                    try {
                    Order processedOrder = service.processNewOrder(view.getNewOrder(products, states), view.getFutureDate());

                    System.out.println("Success! Added order #" + processedOrder.getOrderId());

                } catch (InvalidDateException | InvalidStateException | InvalidProductException | InsufficientAreaException e) {
                    System.out.println(e.getMessage());

                }
                break;
                case 3:
                    try {
                    LocalDate editDate = view.getFutureDate();
                    Order editedOrder = service.editOrder(view.getEditedOrder(service.getAllOrders(editDate), states, products), editDate);
                    System.out.println("Success! Edited order #" + editedOrder.getOrderId());
                } catch (NoRecordsException | OrderNotFoundException | InvalidStateException | InvalidProductException | InsufficientAreaException e) {
                    System.out.println(e.getMessage());
                }
                break;
                case 4:
                    try {
                    System.out.println("Enter order date in YYYY-MM-DD format: ");
                    String userDateString = userInput.nextLine();
                    LocalDate userDate = LocalDate.parse(userDateString);
                    Collection<Order> orders = service.getAllOrders(userDate);
                    for (Order order : orders) {
                        System.out.println(order.getOrderId() + " " + order.getCustomerName() + " " + order.getTotal());
                    }
                    System.out.println("Enter order Id");
                    String orderId = userInput.nextLine();
                    Order orderToDelete = service.getOrder(orderId, userDate);
                    Order deletedOrder = service.deleteOrder(orderToDelete, userDate);
                    System.out.println("Success! Deleted order #" + deletedOrder.getOrderId());
                } catch (NoRecordsException | OrderNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                break;
                case 6:
                    System.out.println("Thank you for using Flooring Program!");
                    usingApp = false;
            }

        }
    }
}
