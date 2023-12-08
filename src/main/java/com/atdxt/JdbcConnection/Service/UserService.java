package com.atdxt.JdbcConnection.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.atdxt.JdbcConnection.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;




public interface UserService {

    List <User> getAllUsers();

    User createUser(User user);

    User getUserById(Integer userId);

    User updateUser(Integer userId, User user);



}
