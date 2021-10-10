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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 29, 2021 purpose:
 */
@Component
public class FlooringMasteryController {

    private final FlooringMasteryService service;
    private final FlooringMasteryView view;

    @Autowired
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
            try {
                int userSelection = view.displayMenuAndGetSelection();
                switch (userSelection) {
                    case 1:
                        displayAllOrders();
                        break;
                    case 2:
                        addOrder(products, states);
                        break;
                    case 3:
                        editOrder(products, states);
                        break;
                    case 4:
                        deleteOrder();
                        break;
                    case 6:
                        usingApp = displayExitMessage();
                }

            } catch (NoRecordsException | OrderNotFoundException | InvalidStateException
                    | InvalidDateException | InvalidProductException | InsufficientAreaException
                    | InvalidCustomerNameException | DaoException e) {
                displayErrorMessage(e.getMessage());
            }
        }
    }

    private void displayAllOrders() throws NoRecordsException {
        view.displayAllOrders(service.getAllOrders(view.getDate()));
    }

    private void addOrder(Collection<Product> products, Collection<StateTax> states)
            throws InvalidDateException, InsufficientAreaException, InvalidStateException,
            DaoException, InvalidProductException, InvalidCustomerNameException {
        LocalDate addDate = view.getFutureDate();
        Order orderToAdd = view.getNewOrder(products, states);
        if (view.confirmAddOrder(orderToAdd, addDate)) {
            Order processedOrder = service.processNewOrder(orderToAdd, addDate);
            view.printMessage("Success! Added order #" + processedOrder.getOrderId());
        }
    }

    private void editOrder(Collection<Product> products, Collection<StateTax> states)
            throws NoRecordsException, DaoException, OrderNotFoundException,
            InvalidProductException, InvalidStateException, InsufficientAreaException,
            InvalidDateException, InvalidCustomerNameException {
        LocalDate editDate = view.getFutureDate();
        Order editedOrder = view.getEditedOrder(service.getAllOrders(editDate), states, products);
        if (view.confirmEditOrder(editedOrder, editDate)) {
            Order processedOrder = service.editOrder(editedOrder, editDate);
            view.printMessage("Success! Edited order #" + processedOrder.getOrderId());
        }
    }

    private void deleteOrder() throws NoRecordsException, DaoException, OrderNotFoundException {
        LocalDate deleteDate = view.getFutureDate();
        Order orderToDelete = view.getOrderToDelete(service.getAllOrders(deleteDate));
        if (view.confirmDeleteOrder(orderToDelete, deleteDate)) {
            Order deletedOrder = service.deleteOrder(orderToDelete, deleteDate);
            view.printMessage("Success! Deleted order #" + deletedOrder.getOrderId());
        }
    }
    
    private void displayErrorMessage(String message) {
        view.printMessage(message);
    }
    
    private boolean displayExitMessage() {
        view.printMessage("Thank you for using Flooring Program!");
        return false;
    }
    
    
}
