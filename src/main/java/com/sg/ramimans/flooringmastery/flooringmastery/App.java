package com.sg.ramimans.flooringmastery.flooringmastery;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.OrderDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.ProductDaoFileImpl;
import com.sg.ramimans.flooringmastery.dao.StateTaxDaoFileImpl;
import com.sg.ramimans.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.sg.ramimans.flooringmastery.controller.FlooringMasteryController;
import com.sg.ramimans.flooringmastery.dao.OrderDao;
import com.sg.ramimans.flooringmastery.dao.ProductDao;
import com.sg.ramimans.flooringmastery.dao.StateTaxDao;
import com.sg.ramimans.flooringmastery.service.FlooringMasteryService;
import com.sg.ramimans.flooringmastery.userio.FlooringMasteryView;
import com.sg.ramimans.flooringmastery.userio.UserIO;
import com.sg.ramimans.flooringmastery.userio.UserIOConsoleImpl;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 26, 2021 purpose:
 */
public class App {

    public static void main(String[] args) throws DaoException {
        
        UserIO io = new UserIOConsoleImpl();
        FlooringMasteryView view = new FlooringMasteryView(io);
        OrderDao orderDao = new OrderDaoFileImpl();
        ProductDao productDao = new ProductDaoFileImpl();
        StateTaxDao statesDao = new StateTaxDaoFileImpl();
        FlooringMasteryService service = new FlooringMasteryServiceLayerImpl(orderDao, productDao, statesDao);
        FlooringMasteryController controller = new FlooringMasteryController(service, view);
        controller.run();
        
        
    }
}
