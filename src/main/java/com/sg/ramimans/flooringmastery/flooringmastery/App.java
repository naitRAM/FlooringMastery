package com.sg.ramimans.flooringmastery.flooringmastery;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Sep. 26, 2021 purpose:
 */
public class App {

    public static void main(String[] args) throws DaoException {
        /*
        UserIO io = new UserIOConsoleImpl();
        FlooringMasteryView view = new FlooringMasteryView(io);
        OrderDao orderDao = new OrderDaoFileImpl();
        ProductDao productDao = new ProductDaoFileImpl();
        StateTaxDao statesDao = new StateTaxDaoFileImpl();
        AuditDao auditDao = new AuditDaoFileImpl();
        FlooringMasteryService service = new FlooringMasteryServiceLayerImpl(orderDao, productDao, statesDao, auditDao);
        FlooringMasteryController controller = new FlooringMasteryController(service, view);
        */
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.ramimans.flooringmastery");
        appContext.refresh();
        FlooringMasteryController  controller = appContext.getBean("flooringMasteryController", FlooringMasteryController.class);
        controller.run();
        
        
    }
}
