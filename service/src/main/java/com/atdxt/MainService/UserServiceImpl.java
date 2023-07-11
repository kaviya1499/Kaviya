package com.atdxt.MainService;

import com.atdxt.Entity.*;
import com.atdxt.RepositoryService.AuthRepository;
import com.atdxt.RepositoryService.DetailsRepository;
import com.atdxt.RepositoryService.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.regex.Pattern;
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

    public UserEntity createUser( @RequestBody UserRequest userreq) {
        Details_Entity det = new Details_Entity();
        det.setEmail(userreq.getEmail());
        det.setDesignation(userreq.getDesignation());
        det.setState(userreq.getState());
        det.setCountry(userreq.getCountry());
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


            String email = userreq.getEmail();

            if(email != null){
                details.setEmail(userreq.getEmail());
            }


            details.setEmail(email);
            details.setDesignation(userreq.getDesignation());
            details.setState(userreq.getState());
            details.setCountry(userreq.getCountry());
            details = detailsRepository.save(details);

            user.setName(userreq.getName());
            user.setAge(userreq.getAge());
            user.setAddress(userreq.getAddress());

            user = userRepository.save(user);

            return user;


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
                .map(authEntity -> new AuthDecode(authEntity.getUsername(), decodeBase64(authEntity.getPassword()), authEntity.getCreatedon(), authEntity.getModified()))
                .collect(Collectors.toList());
    }

    private String decodeBase64(String encodedValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
        return new String(decodedBytes);
    }









    private static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Set<String> UNIQUE_EMAILS = new HashSet<>();

    public boolean isValid(String email) {
        return EMAIL_REGEX_PATTERN.matcher(email).matches();
    }

  /*  public boolean isUnique(String email) {
        if (UNIQUE_EMAILS.contains(email)) {
            return true; // Email is not unique
        } else {
            UNIQUE_EMAILS.add(email);
            return false; // Email is unique
        }
    }*/

    public boolean isEmailUnique(String email) {
        Optional<Details_Entity> existingUser = detailsRepository.findByEmail(email);
        return existingUser.isEmpty();
    }



}





