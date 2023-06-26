package com.atdxt.MainService;

import com.atdxt.MainService.UserService;
import com.atdxt.Entity.UserEntity;
import com.atdxt.RepositoryService.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    /*public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(Integer userId){
        Optional<UserEntity> getuser = userRepository.findById(userId);
        return getuser.get();
    }

    @Override
    public  UserEntity updateUser(UserEntity userEntity){
       UserEntity user = userRepository.findById(userEntity.getId()).get();
        user.setAddress(userEntity.getAddress());
        user.setAge(userEntity.getAge());
        user.setName(userEntity.getName());


        UserEntity UpdateUser = userRepository.save(user);
        return UpdateUser;

    }

}
