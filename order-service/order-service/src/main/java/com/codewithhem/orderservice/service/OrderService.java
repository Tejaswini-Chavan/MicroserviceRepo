package com.codewithhem.orderservice.service;

import com.codewithhem.orderservice.model.Order;
import com.codewithhem.orderservice.repo.OrderCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderCRUD orderCRUD;


    public Order createOrder(Order order) {
        order =orderCRUD.save(order);
        order.setStatus("CREATED");
        return order;
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderCRUD.findAll();
    }
}
