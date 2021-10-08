package com.sg.ramimans.flooringmastery.service;

import com.sg.ramimans.flooringmastery.dao.DaoException;
import com.sg.ramimans.flooringmastery.dao.ProductDao;
import com.sg.ramimans.flooringmastery.model.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Oct. 7, 2021 purpose:
 */
public class ProductDaoStubImpl implements ProductDao {

    Map<String, Product> products = new HashMap<>();
    

    public ProductDaoStubImpl() {
        String firstProductName = "Straw";
        BigDecimal firstProductRate = new BigDecimal("0.50").setScale(2, RoundingMode.HALF_UP);
        BigDecimal firstProductLabourRate = new BigDecimal("0.25").setScale(2, RoundingMode.HALF_UP);
        String secondProductName = "Laminate";
        BigDecimal secondProductRate = new BigDecimal("4.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal secondProductLabourRate = new BigDecimal("2.50").setScale(2, RoundingMode.HALF_UP);
        products.put(firstProductName, new Product(firstProductName, firstProductRate, firstProductLabourRate));
        products.put(secondProductName, new Product(secondProductName, secondProductRate, secondProductLabourRate));
    }

    @Override
    public Product getProduct(String productName) throws DaoException {
        return products.get(productName);
        
    }

    @Override
    public Collection<Product> getAllProducts() throws DaoException {
        
        return products.values();
    }

}
