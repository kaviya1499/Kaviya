package com.atdxt.MainService;

import com.atdxt.MainService.UserService;
import com.atdxt.Entity.UserEntity;
import com.atdxt.RepositoryService.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

}
