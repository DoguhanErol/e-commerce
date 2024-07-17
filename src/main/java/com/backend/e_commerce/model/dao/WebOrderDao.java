package com.backend.e_commerce.model.dao;

import com.backend.e_commerce.model.LocalUser;
import com.backend.e_commerce.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDao extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}
