package com.atdxt.Entity;

import com.atdxt.Entity.Details_Entity;
import com.atdxt.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

 private String Name;
 private String Age;
 private String Address;
 private String imgurl;
 private String Email;
 private String designation;
 private String state;
 private String country;
 private String username;
 private String password;
 private String confirm_password;



}