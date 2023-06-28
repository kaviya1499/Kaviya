package com.atdxt.MainService;

import com.atdxt.Entity.Details_Entity;
import com.atdxt.Entity.UserRequest;
import com.atdxt.MainService.UserService;
import com.atdxt.Entity.UserEntity;
import com.atdxt.RepositoryService.DetailsRepository;
import com.atdxt.RepositoryService.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    /*public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

   /* @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }*/

    public UserEntity createUser(@RequestBody UserRequest userreq) {
        Details_Entity det = new Details_Entity();
        det.setEmail(userreq.getEmail());
        det.setDesignation(userreq.getDesignation());
        det = detailsRepository.save(det);

        UserEntity userEntity = new UserEntity(userreq);
        userEntity.setDetailsEntity(det);
        return userRepository.save(userEntity);

    }


    @Override
    public UserEntity getUserById(Integer userId) {
        Optional<UserEntity> getuser = userRepository.findById(userId);
        return getuser.get();
    }

    @Override
    public UserEntity updateUser(Integer userId, @RequestBody UserRequest userreq) {
        //UserEntity user = userRepository.findById(userEntity.getId()).get();
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            Details_Entity details = user.getDetailsEntity();
            if (details == null) {
                details = new Details_Entity();
                user.setDetailsEntity(details);
            }

            details.setEmail(userreq.getEmail());
            details.setDesignation(userreq.getDesignation());
            details = detailsRepository.save(details);

            user.setName(userreq.getName());
            user.setAge(userreq.getAge());
            user.setAddress(userreq.getAddress());

            return userRepository.save(user);
        }

        return null;

    }
}