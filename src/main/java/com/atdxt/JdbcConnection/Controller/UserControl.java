package com.atdxt.JdbcConnection.Controller;


import java.util.List;

import com.atdxt.JdbcConnection.Model.User;
import com.atdxt.JdbcConnection.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserControl {

    public static final Logger logging= LoggerFactory.getLogger(UserControl.class);

    @Autowired
    public UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){

        try {
            logging.debug("Debug messages.......... ");
            List<User> users = userService.getAllUsers();
            logging.info("Fetched Users {}",users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            User savedUser = userService.createUser(user);
            logging.info("Users Inserted Successfully......");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        }catch (Exception e) {
            logging.error("Error occurred while Inserting : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
