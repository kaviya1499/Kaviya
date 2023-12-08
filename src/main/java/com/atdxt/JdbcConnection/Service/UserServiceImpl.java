package com.atdxt.JdbcConnection.Service;

import com.atdxt.JdbcConnection.Model.User;
import com.atdxt.JdbcConnection.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserById(Integer userId) {
        Optional<User> getuser = userRepository.findById(userId);
        return getuser.get();
    }

    @Override
    public User updateUser(Integer userId, User user){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User existingUser=optionalUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setName(user.getName());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());
            return userRepository.save(existingUser);
        }else {
            return null;
        }

    }

}
