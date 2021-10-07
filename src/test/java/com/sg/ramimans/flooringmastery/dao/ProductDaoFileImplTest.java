/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Product;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author rmans
 */
public class ProductDaoFileImplTest {
    
    ProductDao testDao;
    public ProductDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() {
        this.testDao = new ProductDaoFileImpl("Tests/ProductDao/Products.txt");
    }
    
    @Test
    public void testGetProducts() throws Exception {
        Collection<Product> products = testDao.getAllProducts();
        for (Product product : products) {
            Product returnedProduct = testDao.getProduct(product.getProduct());
            assertEquals(product, returnedProduct, "Product must have all properties equal to returnedProduct");
        }
    }
}
