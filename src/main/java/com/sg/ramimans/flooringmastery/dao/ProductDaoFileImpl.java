package com.sg.ramimans.flooringmastery.dao;

import com.sg.ramimans.flooringmastery.model.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Sep. 27, 2021
 * purpose: 
 */
public class ProductDaoFileImpl {
    private final String DELIMITER = ",";
    private final String fileName;
    private Map<String, Product> products = new HashMap<>();
    
    public ProductDaoFileImpl(String fileName) {
        this.fileName = fileName;
    }
    
    public ProductDaoFileImpl() {
        this.fileName = "Data/Products.txt";
    }
    
    private void readProducts() throws DaoException {
        Scanner fileInput;
        try {
            fileInput = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new DaoException("Could not read Product data", e);
        }
        
        String currentLine;
        Product currentProduct;
        
        while (fileInput.hasNextLine()) {
            currentLine = fileInput.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            this.products.put(currentProduct.getProduct(), currentProduct);
        }
    }
    
    private Product unmarshallProduct(String entry) {
        String[] entryArray = entry.split(DELIMITER);
        String productName = entryArray[0];
        BigDecimal productRate = new BigDecimal(entryArray[1]).setScale(2, RoundingMode.HALF_UP);
        BigDecimal labourRate = new BigDecimal(entryArray[2]).setScale(2, RoundingMode.HALF_UP);
        return new Product(productName, productRate, labourRate);
    }
    
    public Product getProduct(String productName) throws DaoException {
        this.readProducts();
        return this.products.get(productName);
    }
    
    public Collection<Product> getAllProducts() throws DaoException {
        this.readProducts();
        return this.products.values();
    }
}
