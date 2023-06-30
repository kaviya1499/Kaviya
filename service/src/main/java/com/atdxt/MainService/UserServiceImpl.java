package com.atdxt.MainService;

import com.atdxt.Entity.*;
import com.atdxt.RepositoryService.AuthRepository;
import com.atdxt.RepositoryService.DetailsRepository;
import com.atdxt.RepositoryService.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private AuthRepository authRepository;

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

    @Override
    public Auth_Entity CreateAuth(@RequestBody Auth_Entity authEntity){
        Auth_Entity auth_entity = new Auth_Entity();
        auth_entity.setUsername(authEntity.getUsername());
        String password = authEntity.getPassword();
        String passwordEncode= Base64.getEncoder().encodeToString(password.getBytes());
        auth_entity.setPassword(passwordEncode);
        return authRepository.save(auth_entity);

    }

    @Override
    public List<AuthDecode> getAllAuth() {
        List<Auth_Entity> authEntities = authRepository.findAll();
        List<AuthDecode> authDtos = decodePasswords(authEntities);
        return authDtos;
    }


    private List<AuthDecode> decodePasswords(List<Auth_Entity> authEntities) {
        return authEntities.stream()
                .map(authEntity -> new AuthDecode(authEntity.getUsername(), decodeBase64(authEntity.getPassword())))
                .collect(Collectors.toList());
    }

    private String decodeBase64(String encodedValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
        return new String(decodedBytes);
    }









}