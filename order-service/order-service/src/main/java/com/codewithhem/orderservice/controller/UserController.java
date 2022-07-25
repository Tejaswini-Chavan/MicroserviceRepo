package com.codewithhem.orderservice.controller;

import com.codewithhem.orderservice.dto.UserDTO;
import com.codewithhem.orderservice.model.User;
import com.codewithhem.orderservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    KafkaTemplate<String,User> userKafkaTemplate;

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setBalance(userDTO.getBalance());
        user.setName(userDTO.getName());
        user = userService.saveUser(user);
        userKafkaTemplate.send("UserTopic",user);
        return user;
    }
}
