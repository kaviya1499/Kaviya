package com.atdxt.MainService;

import com.atdxt.Entity.*;

import java.util.List;

public interface UserService {


    List<UserEntity> getAllUsers();

    UserEntity createUser(UserRequest userreq);

    UserEntity getUserById(Integer userId);

    UserEntity updateUser(Integer userId,UserRequest userreq);

    Auth_Entity CreateAuth(Auth_Entity authEntity);
    List<AuthDecode> getAllAuth();

     boolean isValid(String email);
    boolean isEmailUnique(String email);


}

