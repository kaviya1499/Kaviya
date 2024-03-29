package com.atdxt.MainService;

import com.atdxt.Entity.*;
import com.atdxt.RepositoryService.AuthRepository;
import com.atdxt.RepositoryService.DetailsRepository;
import com.atdxt.RepositoryService.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    private final Environment environment;

    /*public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    @Override
    public List<UserEntity> getAllUsers() {

        List<UserEntity> users = userRepository.findAll();


        return userRepository.findAll();


    }








   /* @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }*/

    public UserEntity createUser( @RequestBody UserRequest userreq) {

        System.out.println(userreq.getUsername()+userreq.getPassword());
        Details_Entity det = new Details_Entity();
        det.setEmail(userreq.getEmail());
        det.setDesignation(userreq.getDesignation());
        det.setState(userreq.getState());
        det.setCountry(userreq.getCountry());
        det = detailsRepository.save(det);


        Auth_Entity auth=new Auth_Entity();
        auth.setUsername(userreq.getUsername());
        String password = userreq.getPassword();

       // String passwordEncode= Base64.getEncoder().encodeToString(password.getBytes());
        String encodepassword=passwordEncoder.encode(password);
        auth.setPassword(encodepassword);
        String confirm_password=userreq.getConfirm_password();
       // String confirmpasswordEncode= Base64.getEncoder().encodeToString(confirm_password.getBytes());
        String confirmpasswordEncode=passwordEncoder.encode(confirm_password);
        auth.setConfirm_password(confirmpasswordEncode);

        auth=authRepository.save(auth);

        UserEntity userEntity = new UserEntity(userreq);
        System.out.println(userreq.getImgurl());
        userEntity.setImgurl(userreq.getImgurl());
        userEntity.setDetailsEntity(det);
        userEntity.setAuthEntity(auth);
        return userRepository.save(userEntity);

    }


    @Override
    public UserEntity getUserById(Integer userId) {
        Optional<UserEntity> getuser = userRepository.findById(userId);
        return getuser.get();
    }

    @Override
    public UserEntity updateUser(Integer userId, @ModelAttribute("userreq") UserRequest userreq) {
        //UserEntity user = userRepository.findById(userEntity.getId()).get();
        Optional<UserEntity> optionalUser = userRepository.findById(userId);


        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            Details_Entity details = user.getDetailsEntity();
            if (details == null) {
                details = new Details_Entity();
                user.setDetailsEntity(details);
            }
            Auth_Entity auth= user.getAuthEntity();
            if(auth == null){
                auth=new Auth_Entity();
                user.setAuthEntity(auth);
            }

            auth.setUsername(userreq.getUsername());
             auth.setPassword(userreq.getPassword());
            auth=authRepository.save(auth);


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
                .map(authEntity -> new AuthDecode(authEntity.getUsername(), decodeBase64(authEntity.getPassword()), authEntity.getCreatedon(), authEntity.getModified(),decodeBase64(authEntity.getConfirm_password())))
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



    public boolean isEmailUnique1(String email) {
        Optional<Details_Entity> existingUser = detailsRepository.findByEmail(email);
               return existingUser.isEmpty();
    }


   public boolean isEmailUnique(String email,Integer id) {
       Optional<Details_Entity> userOptional = detailsRepository.findByEmail(email);
       if (userOptional.isPresent()) {
           Details_Entity existingUser = userOptional.get();
           return !existingUser.getDetid().equals(id);
       }
       return false;

    }



    public boolean isValidName(String name) {
        // Regular expression pattern
        String pattern = "^[a-zA-Z\\s]+$";

        // Check if the name matches the pattern
        return !name.matches(pattern);
    }

    public boolean isUsernameUnique(String username) {
        Optional<Auth_Entity> existingUsername = authRepository.findByUsername(username);
        return existingUsername.isEmpty();
    }





    @Override
    public String sendMail(String to,String subject,String body) throws MessagingException {

        String fromEmail = environment.getProperty("spring.mail.username");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(to);

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);



        javaMailSender.send(mimeMessage);

        return "mail send";
    }

    @Override
    public String sendPasswordResetEmail(Auth_Entity authEntity,String email) throws MessagingException {
        String resetToken = UUID.randomUUID().toString();
        authEntity.setResetToken(resetToken);
        authEntity.setResetTokenExpiry(LocalDateTime.now().plusHours(24)); // Token expiry in 24 hours
        authRepository.save(authEntity);
        String url = environment.getProperty("app.url");

        // Send the password reset email
        String subject = "Password Reset";
        String body = "Hi " + authEntity.getUsername() + ",\n\n"
                + "Please click on the link below to reset your password:\n"
                + url + "/passwordreset?token=" + resetToken ;

        return sendMail(email, subject, body);

    }


    @Override
    public String resetPassword(Auth_Entity authEntity, String password,String confirmpassword) {
        // Update the user's password and clear the reset token

        authEntity.setPassword(passwordEncoder.encode(password));
        authEntity.setConfirm_password(passwordEncoder.encode(confirmpassword));
        authEntity.setResetToken(null);
        authEntity.setResetTokenExpiry(null);
        authRepository.save(authEntity);
        return "password reset";

    }


}





