package com.codewithhem.orderservice.controller;

import com.codewithhem.orderservice.dto.OrderDTO;
import com.codewithhem.orderservice.model.Order;
import com.codewithhem.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "app/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    KafkaTemplate<String, Order> orderKafkaTemplate;

    @PostMapping(value = "/create")
    public Order createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderAmount(orderDTO.getOrderAmount());
        order.setId(orderDTO.getId());
        order.setStatus(orderDTO.getStatus());
        order.setUserId(orderDTO.getUserId());
        order=orderService.createOrder(order);
        orderKafkaTemplate.send("OrderTopic", order);
        return order;
    }

    @GetMapping("/all")
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }
}
