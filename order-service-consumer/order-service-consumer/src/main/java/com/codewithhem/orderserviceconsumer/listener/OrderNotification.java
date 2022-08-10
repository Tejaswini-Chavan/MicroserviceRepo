package com.codewithhem.orderserviceconsumer.listener;

import com.codewithhem.orderserviceconsumer.model.Order;
import com.codewithhem.orderserviceconsumer.model.User;
import com.codewithhem.orderserviceconsumer.repo.OrderCRUD;
import com.codewithhem.orderserviceconsumer.repo.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    
    //recieves order object from OrderServiceMS
    //This method will update order status and user balance.
    @KafkaListener(topics = "OrderTopic", groupId= "group_order" , containerFactory = "orderKafkaListenerFactory")
    public Order listenOrderNotification(Order order) {
        Optional<User> optional = userCRUD.findById(order.getUserId());
        User user = new User();
        if(optional.isPresent())
            user = optional.get();
        //check if order amount is less than user balance and set status to 'SUCCESS' or 'FAILED'
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
    
    //sends order and user objects to OrderServiceMS through kafka
    public void sendOrderAndUserNotification(User user, Order order){
        userKafkaTemplate.send("RevUserTopic", user);
        orderKafkaTemplate.send("RevOrderTopic",order);
    }
    
    //This method will deduct order amount from user's balance
    public User setUserBalance(User user, double amount){
        user.setBalance(user.getBalance() - amount);
        return user;
    }
}
