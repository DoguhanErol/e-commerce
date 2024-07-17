package com.backend.e_commerce.service;

import com.backend.e_commerce.model.LocalUser;
import com.backend.e_commerce.model.WebOrder;
import com.backend.e_commerce.model.dao.WebOrderDao;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    private WebOrderDao webOrderDao;

    public OrderService(WebOrderDao webOrderDao) {
        this.webOrderDao = webOrderDao;
    }
    public List<WebOrder> getOrders(LocalUser user){
        return webOrderDao.findByUser(user);
    }
}
