package com.codewithhem.orderservice.listener;

import com.codewithhem.orderservice.model.Order;
import com.codewithhem.orderservice.model.User;
import com.codewithhem.orderservice.repo.OrderCRUD;
import com.codewithhem.orderservice.repo.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @Autowired
    UserCRUD userCRUD;

    @Autowired
    OrderCRUD orderCRUD;

    @KafkaListener(topics = "RevUserTopic", groupId = "group_user", containerFactory = "userKafkaListenerFactory")
    public void saveUpdatedUser(User user){
        userCRUD.save(user);
    }

    @KafkaListener(topics = "RevOrderTopic", groupId = "group_order", containerFactory = "orderKafkaListenerFactory")
    public void saveUpdatedOrder(Order order){
        orderCRUD.save(order);
    }

}
