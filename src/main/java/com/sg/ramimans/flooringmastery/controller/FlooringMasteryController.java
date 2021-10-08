package com.sg.ramimans.flooringmastery.controller;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.service.InsufficientAreaException;
import com.sg.ramimans.flooringmastery.service.InvalidDateException;
import com.sg.ramimans.flooringmastery.service.InvalidProductException;
import com.sg.ramimans.flooringmastery.service.InvalidStateException;
import com.sg.ramimans.flooringmastery.service.NoRecordsException;
import com.sg.ramimans.flooringmastery.service.OrderNotFoundException;
import com.sg.ramimans.flooringmastery.model.Order;
import com.sg.ramimans.flooringmastery.model.Product;
import com.sg.ramimans.flooringmastery.model.StateTax;
import com.sg.ramimans.flooringmastery.service.FlooringMasteryService;
import com.sg.ramimans.flooringmastery.service.InvalidCustomerNameException;
import com.sg.ramimans.flooringmastery.userio.FlooringMasteryView;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 29, 2021 purpose:
 */
public class FlooringMasteryController {

    private final FlooringMasteryService service;
    private final FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
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
                    view.printMessage(e.getMessage());
                }
                break;
                case 2:
                    try {
                    LocalDate addDate = view.getFutureDate();
                    
                    Order orderToAdd = view.getNewOrder(products, states); 
                    if (view.confirmAddOrder(orderToAdd, addDate)) {
                        Order processedOrder = service.processNewOrder(orderToAdd, addDate);
                        view.printMessage("Success! Added order #" + processedOrder.getOrderId());
                    }
                    
                } catch (InvalidDateException | InvalidStateException | InvalidProductException 
                        | InsufficientAreaException | InvalidCustomerNameException e) {
                    view.printMessage(e.getMessage());

                }
                break;
                case 3:
                    try {
                    LocalDate editDate = view.getFutureDate();
                    Order editedOrder = view.getEditedOrder(service.getAllOrders(editDate), states, products);
                    if (view.confirmEditOrder(editedOrder, editDate)) {
                        Order processedOrder = service.editOrder(editedOrder, editDate);
                        view.printMessage("Success! Edited order #" + processedOrder.getOrderId());
                    }
                    
                } catch (NoRecordsException | OrderNotFoundException | InvalidStateException | 
                        InvalidDateException | InvalidProductException | InsufficientAreaException 
                        | InvalidCustomerNameException e) {
                    view.printMessage(e.getMessage());
                }
                break;
                case 4:
                    try {
                    LocalDate deleteDate = view.getFutureDate();              
                    Order orderToDelete = view.getOrderToDelete(service.getAllOrders(deleteDate)); 
                    if (view.confirmDeleteOrder(orderToDelete, deleteDate)) {
                        Order deletedOrder = service.deleteOrder(orderToDelete, deleteDate);
                        view.printMessage("Success! Deleted order #" + deletedOrder.getOrderId());
                    }
                } catch (NoRecordsException | OrderNotFoundException e) {
                    view.printMessage(e.getMessage());
                }
                break;
                case 6:
                    view.printMessage("Thank you for using Flooring Program!");
                    usingApp = false;
            }

        }
    }
}
