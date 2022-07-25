package com.codewithhem.orderserviceconsumer.listener;

import com.codewithhem.orderserviceconsumer.model.Order;
import com.codewithhem.orderserviceconsumer.model.User;
import com.codewithhem.orderserviceconsumer.repo.OrderCRUD;
import com.codewithhem.orderserviceconsumer.repo.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderNotification {

    @Autowired
    OrderCRUD orderCRUD;
    @Autowired
    UserCRUD userCRUD;

    @Autowired
    KafkaTemplate<String, User> userKafkaTemplate;

    @Autowired
    KafkaTemplate<String, Order> orderKafkaTemplate;

    @KafkaListener(topics = "OrderTopic", groupId= "group_order" , containerFactory = "orderKafkaListenerFactory")
    public Order listenOrderNotification(Order order) {
        User user = userCRUD.findById(order.getUserId()).get();
        if (user.getBalance() > order.getOrderAmount()) {
            this.setUserBalance(user, order.getOrderAmount());
            order.setStatus("SUCCESS");
            user = userCRUD.save(user);
            order = orderCRUD.save(order);
            sendOrderAndUserNotification(user, order);
        } else {
            order.setStatus("FAILED");
            order = orderCRUD.save(order);
            orderKafkaTemplate.send("RevOrderTopic",order);
        }
        return order;
    }

    public void sendOrderAndUserNotification(User user, Order order){
        userKafkaTemplate.send("RevUserTopic", user);
        orderKafkaTemplate.send("RevOrderTopic",order);
    }

    public User setUserBalance(User user, double amount){
        user.setBalance(user.getBalance() - amount);
        return user;
    }
}
