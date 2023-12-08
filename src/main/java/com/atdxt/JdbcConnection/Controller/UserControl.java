package com.atdxt.JdbcConnection.Controller;


import java.util.List;

import com.atdxt.JdbcConnection.Model.User;
import com.atdxt.JdbcConnection.Model.login;
import com.atdxt.JdbcConnection.Repository.UserRepository;
import com.atdxt.JdbcConnection.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserControl {

    @Autowired
    public UserService userService;

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/posting")
    public ResponseEntity<User> createUser(@RequestParam("username") String username,
                                           @RequestParam("password") String password,
                                           @RequestParam("name") String name,
                                           @RequestParam("phone") String phone,
                                           @RequestParam("address") String address){

        System.out.println(username);
        System.out.println(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);

        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/getuser")
    public ResponseEntity<User> getUserIdByUsername(@RequestParam("username") String username, @RequestParam("password") String password) {

        Integer userId = userRepository.findIdByUsername(username);

        User users = userService.getUserById(userId);
        if (password.equals(users.getPassword())) {
             System.out.println("Password Matched");
            return ResponseEntity.ok(users);
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // return ResponseEntity.ok(users);

    }


    @PutMapping("/updateuser")
    public ResponseEntity<User> updateUser(@RequestParam("userId") Integer userId, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address){
        System.out.println(username);
        System.out.println(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);

        User updateuser= userService.updateUser(userId,user);

        if(user != null){

            return ResponseEntity.ok(updateuser);
        }else {
            return ResponseEntity.notFound().build();
        }

    }


}
