package com.backend.e_commerce.service;

import com.backend.e_commerce.model.Product;
import com.backend.e_commerce.model.dao.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProducts(){
        return productDao.findAll();
    }
}
