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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class UserControl {

    public static final Logger logging = LoggerFactory.getLogger(UserControl.class);

    @Autowired
    private UserService userService;



  /* public UserControl(UserService userservice) {
       this.userService = userservice;
   }*/

    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }


  /*  @GetMapping("/")
    public ModelAndView getHome() {
        try {
            logging.debug("Debug messages.......... ");


            logging.info("Entering into home page");
            ModelAndView modelAndView = new ModelAndView("users");
            modelAndView.addObject("users", users);
            return modelAndView;
        } catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return new ModelAndView("error", "errorMessage", e.getMessage());
        }
    }*/

    @GetMapping("/register")
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("register");
        UserRequest userreq= new UserRequest();
        modelAndView.addObject("userreq", userreq);
        return modelAndView;
    }

    @GetMapping("/getall")
    public ModelAndView getAllUsers() {
        try {
            logging.debug("Debug messages.......... ");

            List<UserEntity> users = userService.getAllUsers();
            logging.info("Fetched Users {}", users.size());
            ModelAndView modelAndView = new ModelAndView("users");
            modelAndView.addObject("users", users);
            return modelAndView;
        } catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return new ModelAndView("error", "errorMessage", e.getMessage());
        }
    }

/*   @PostMapping("/insert")
    public ResponseEntity<Object> createUser(@RequestBody UserRequest userreq) {
        if (!userService.isValid(userreq.getEmail())) {
            return ResponseEntity.badRequest().body("Email Id: ' " + userreq.getEmail() + " ' Invalid email address provided.");
        }
        Integer id = null;
        if (!userService.isEmailUnique1(userreq.getEmail())) {
            return ResponseEntity.badRequest().body("Email Id: ' " + userreq.getEmail() + " ' Email Already Exists(Duplicate entry)");
        }

        try {
            UserEntity savedUser = userService.createUser(userreq);
            logging.info("Users Inserted Successfully......");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            logging.error("Error occurred while Inserting : {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/





    @PostMapping("/insert")
    public RedirectView createUser(@ModelAttribute("userreq") UserRequest userreq, RedirectAttributes redirectAttributes) {


        if((userreq.getName()).isEmpty()){
            String errorMessage = "User name is mandatory.";
            redirectAttributes.addFlashAttribute("name_error", errorMessage);
            return new RedirectView("/register", true);
        }

        if(userService.isValidName(userreq.getName())){
            String errorMessage = "User name Should not contains Numbers and special symbol.";
            redirectAttributes.addFlashAttribute("name_error", errorMessage);
            return new RedirectView("/register", true);
        }


        if((userreq.getEmail()).isEmpty()){
            String errorMessage = "Email is mandatory.";
            redirectAttributes.addFlashAttribute("email_error", errorMessage);
            return new RedirectView("/register", true);
        }

        if (!userService.isValid(userreq.getEmail())) {

            String errorMessage = "Invalid email address provided.";
            redirectAttributes.addFlashAttribute("email_error", errorMessage);
            return new RedirectView("/register", true);
            //return ResponseEntity.badRequest().body("Email Id: ' " + userreq.getEmail() + " ' Invalid email address provided.");
        }

        if (!userService.isEmailUnique1(userreq.getEmail())) {
            String errorMessage = "Email already Exists.";
            redirectAttributes.addFlashAttribute("email_error", errorMessage);
            return new RedirectView("/register", true);
          //  return ResponseEntity.badRequest().body("Email Id: ' " + userreq.getEmail() + " ' Email Already Exists(Duplicate entry)");
        }

        try {
            UserEntity savedUser = userService.createUser(userreq);
            logging.info("Users Inserted Successfully......");

            return new RedirectView("/login", true);
          //  return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            logging.error("Error occurred while Inserting : {}", e.getMessage());
            return new RedirectView("/home", true);
           // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    @GetMapping("/updateform/{id}")
    public ModelAndView updateForm(@PathVariable("id") Integer userId) {
        UserEntity userreq = userService.getUserById(userId);

        ModelAndView modelAndView = new ModelAndView("updateform");
        modelAndView.addObject("userreq", userreq);
        modelAndView.addObject("detailsEntity", userreq.getDetailsEntity());
        return modelAndView;
    }







    @GetMapping("/get/{id}")
    public ModelAndView getUserById(@PathVariable("id") Integer userId) {
        try {
            //int res = 10/0;
            UserEntity users = userService.getUserById(userId);
            ModelAndView modelAndView = new ModelAndView("users");
            modelAndView.addObject("users", users);
            return modelAndView;

        } catch (Exception e) {
            logging.error("Error occurred while fetching user : {}", e.getMessage());
            return new ModelAndView("error", "errorMessage", e.getMessage());
        }
    }






    @PostMapping("/update/{id}")
    public RedirectView UpdateUser(@PathVariable("id") Integer userId, @ModelAttribute("userreq") UserRequest userreq,RedirectAttributes redirectAttributes) {


        System.out.println(userreq.getName()+userreq.getAge()+userreq.getEmail());

        /*if((userreq.getName()).isEmpty()){
            String errorMessage = "User name is mandatory.";
            redirectAttributes.addFlashAttribute("name_error", errorMessage);
            return new RedirectView("/updateform/"+ userId, true);
        }

        if(userService.isValidName(userreq.getName())){
            String errorMessage = "User name Should not contains Numbers and special symbol.";
            redirectAttributes.addFlashAttribute("name_error", errorMessage);
            return new RedirectView("/updateform/"+ userId, true);
        }

        if (!userService.isValid(userreq.getEmail())) {

            String errorMessage = "Invalid email address provided.";
            redirectAttributes.addFlashAttribute("email_error", errorMessage);
            return new RedirectView("/updateform/"+ userId, true);
            //return ResponseEntity.badRequest().body("Email Id: ' " + userreq.getEmail() + " ' Invalid email address provided.");
        }

        if (!userService.isEmailUnique(userreq.getEmail(),userId)) {
            String errorMessage = "Email already Exists.";
            redirectAttributes.addFlashAttribute("email_error", errorMessage);
            return new RedirectView("/updateform/"+ userId, true);
            //  return ResponseEntity.badRequest().body("Email Id: ' " + userreq.getEmail() + " ' Email Already Exists(Duplicate entry)");
        }*/


        try {
            UserEntity updatedUser = userService.updateUser(userId, userreq);
            if (updatedUser != null) {

                return new RedirectView("/login", true);
              //  return ResponseEntity.ok(updatedUser);
            }
           else {
                return new RedirectView("/home", true);
            }

        } catch (Exception e) {
            logging.error("Error occurred while Updating user : {}", e.getMessage());
            return new RedirectView("/home", true);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }


    @PostMapping("/auth")
    public ResponseEntity<Auth_Entity> AuthCreate(@RequestBody Auth_Entity authEntity) {
        try {
            Auth_Entity savedUser = userService.CreateAuth(authEntity);
            logging.info("Username and Password Inserted Successfully");
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logging.error("Error occured while Inserting username and password : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/getauth")
    public ResponseEntity<List<AuthDecode>> getAllAuth() {
        try {


            List<AuthDecode> users = userService.getAllAuth();
            logging.info("Fetched Usernames and passwords {}", users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logging.error("Error occurred while fetching users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}










