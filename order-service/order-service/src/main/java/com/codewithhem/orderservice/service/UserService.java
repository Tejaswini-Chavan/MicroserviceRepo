package com.codewithhem.orderservice.service;

import com.codewithhem.orderservice.model.User;
import com.codewithhem.orderservice.repo.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class UserService {

    @Autowired
    UserCRUD userCRUD;

    public List<User> getAllUser() {
        return (List<User>) userCRUD.findAll();
    }


    public User saveUser(User user) {
        user = userCRUD.save(user);
        return user;
    }
}
