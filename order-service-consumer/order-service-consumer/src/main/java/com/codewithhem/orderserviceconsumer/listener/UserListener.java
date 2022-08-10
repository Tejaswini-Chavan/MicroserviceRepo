package com.codewithhem.orderserviceconsumer.listener;

import com.codewithhem.orderserviceconsumer.model.User;
import com.codewithhem.orderserviceconsumer.repo.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {

    @Autowired
    UserCRUD userCRUD;
    
    //recieves user object from OrderServiceMS through kafka
    @KafkaListener(topics = "UserTopic", groupId= "group_user" , containerFactory = "userKafkaListenerFactory")
    public User saveUser(User user){
        return userCRUD.save(user);
    }


}
