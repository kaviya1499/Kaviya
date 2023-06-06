package com.atdxt.JdbcConnection.Controller;


import java.util.List;

import com.atdxt.JdbcConnection.Model.User;
import com.atdxt.JdbcConnection.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/users")
public class UserControl {

    @Autowired
    public UserService dao;

    @RequestMapping("")
    public List<User> customerInformation() {
        List<User> Users = dao.isData();
        return Users;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        dao.insertUser(user);
        return ResponseEntity.ok("User created successfully");
    }
}
