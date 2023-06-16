package com.atdxt.ControllerService;

import com.atdxt.Entity.UserEntity;
import com.atdxt.MainService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/getinfo")
public class UserControl {

    @Autowired
    private UserService userService;



   public UserControl(UserService userservice) {
       this.userService = userservice;
   }


    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){

        UserEntity savedUser = userService.createUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        }





}
