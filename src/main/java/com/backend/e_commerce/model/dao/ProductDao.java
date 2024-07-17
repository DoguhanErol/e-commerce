package com.backend.e_commerce.model.dao;

import com.backend.e_commerce.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDao extends ListCrudRepository<Product, Long> {
}
