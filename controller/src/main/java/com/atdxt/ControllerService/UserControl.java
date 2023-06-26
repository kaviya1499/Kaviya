package com.atdxt.ControllerService;

import com.atdxt.Entity.UserEntity;
import com.atdxt.MainService.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@AllArgsConstructor
@RequestMapping("/getinfo")
public class UserControl {

    public static final Logger logging= LoggerFactory.getLogger(UserControl.class);

    @Autowired
    private UserService userService;



  /* public UserControl(UserService userservice) {
       this.userService = userservice;
   }*/


    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        try {
            logging.debug("Debug messages.......... ");
           int res =10/0;

            List<UserEntity> users = userService.getAllUsers();
            logging.info("Fetched Users {}",users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        try {
            UserEntity savedUser = userService.createUser(user);
            logging.info("Users Inserted Successfully......");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        }catch (Exception e) {
            logging.error("Error occurred while Inserting : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

        @GetMapping("{id}")
        public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Integer userId){
        try {
            UserEntity userEntity = userService.getUserById(userId);
            logging.info("User Fetched Successfully......");
            return new ResponseEntity<>(userEntity, HttpStatus.OK);
        }catch (Exception e){
            logging.error("Error occurred while Fetching data : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        }

        @PutMapping("{id}")
        public ResponseEntity<UserEntity> UpdateUser(@PathVariable("id") Integer userId, @RequestBody UserEntity userEntity){
        try {
            userEntity.setId(userId);
            UserEntity updateuser = userService.updateUser(userEntity);
            return new ResponseEntity<>(updateuser, HttpStatus.OK);
        }catch (Exception e){
            logging.error("Error occurred while Updating the data into table : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        }





}
