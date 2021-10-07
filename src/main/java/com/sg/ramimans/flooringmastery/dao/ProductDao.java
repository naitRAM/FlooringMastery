/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Product;
import java.util.Collection;

/**
 *
 * @author rmans
 */
public interface ProductDao {
    public Product getProduct(String productName) throws DaoException;
    public Collection<Product> getAllProducts() throws DaoException;
}
