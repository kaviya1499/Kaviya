package com.atdxt.ControllerService;

import com.atdxt.Entity.*;
import com.atdxt.MainService.UserService;
import com.atdxt.RepositoryService.AuthRepository;
import com.atdxt.RepositoryService.DetailsRepository;
import com.atdxt.RepositoryService.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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

            List<UserEntity> users = userService.getAllUsers();
            logging.info("Fetched Users {}",users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUser( @RequestBody UserRequest userreq){
        if (!EmailValidator.isValid(userreq.getEmail())) {
            return ResponseEntity.badRequest().body("Email Id: ' "+ userreq.getEmail() + " ' Invalid email address provided.");
        }
        if (!EmailValidator.isUnique(userreq.getEmail())) {
            return ResponseEntity.badRequest().body("Email Id: ' "+ userreq.getEmail() +" ' Email Already Exists(Duplicate entry)");
        }

        try {
            UserEntity savedUser = userService.createUser(userreq);
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
            //int res = 10/0;
            UserEntity userEntity = userService.getUserById(userId);
            return new ResponseEntity<>(userEntity, HttpStatus.OK);
        }catch (Exception e) {
            logging.error("Error occurred while fetching user : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<UserEntity> UpdateUser(@PathVariable("id") Integer userId, @RequestBody UserRequest userreq){
      try {
          UserEntity updatedUser = userService.updateUser(userId, userreq);
          if (updatedUser != null) {
              return ResponseEntity.ok(updatedUser);
          } else {
              return ResponseEntity.notFound().build();
          }

      }catch (Exception e) {
          logging.error("Error occurred while Updating user : {}", e.getMessage());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }


    }




    @PostMapping("/auth")
    public ResponseEntity<Auth_Entity> AuthCreate(@RequestBody Auth_Entity authEntity){
        try {
            Auth_Entity savedUser = userService.CreateAuth(authEntity);
            logging.info("Username and Password Inserted Successfully");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch (Exception e){
            logging.error("Error occured while Inserting username and password : {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/getauth")
    public ResponseEntity<List<AuthDecode>> getAllAuth(){
        try {


            List<AuthDecode> users = userService.getAllAuth();
            logging.info("Fetched Usernames and passwords {}",users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}


class EmailValidator {
    private static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Set<String> UNIQUE_EMAILS = new HashSet<>();

    public static boolean isValid(String email) {
        return EMAIL_REGEX_PATTERN.matcher(email).matches();
    }

    public static boolean isUnique(String email) {
        if (UNIQUE_EMAILS.contains(email)) {
            return true; // Email is not unique
        } else {
            UNIQUE_EMAILS.add(email);
            return false; // Email is unique
        }
    }
}




