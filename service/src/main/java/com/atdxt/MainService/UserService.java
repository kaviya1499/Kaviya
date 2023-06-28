package com.atdxt.MainService;

import com.atdxt.Entity.Details_Entity;
import com.atdxt.Entity.UserEntity;
import com.atdxt.Entity.UserRequest;

import java.util.List;

public interface UserService {


    List<UserEntity> getAllUsers();

    UserEntity createUser(UserRequest userreq);

    UserEntity getUserById(Integer userId);

    UserEntity updateUser(Integer userId,UserRequest userreq);


}

