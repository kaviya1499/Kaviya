package com.atdxt.MainService;

import com.atdxt.Entity.UserEntity;

import java.util.List;

public interface UserService {


    List<UserEntity> getAllUsers();

    UserEntity createUser(UserEntity user);

    UserEntity getUserById(Integer userId);

    UserEntity updateUser(UserEntity userEntity);


}
