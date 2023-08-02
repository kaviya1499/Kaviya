package com.atdxt.ControllerService;

import com.atdxt.Entity.Auth_Entity;
import com.atdxt.MainService.UserService;
import com.atdxt.RepositoryService.AuthRepository;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.config.Customizer.withDefaults;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {



    private JpaUserDetailsService myUserDetailsService;

    public WebSecurityConfig(JpaUserDetailsService myUserDetailsService){
        this.myUserDetailsService=myUserDetailsService;
    }

  /*  @Bean
    InMemoryUserDetailsManager users(){
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password("{noop}password")
                        .build()
        );
    }*/


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {




        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/insert").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/send").permitAll()
                        .requestMatchers("/mail").permitAll()
                        .requestMatchers("/passwordreset").permitAll()
                        .requestMatchers("/resetpassword").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(myUserDetailsService)
                .formLogin(formLogin-> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/getuserdetails"))

                .httpBasic(withDefaults());

     /*   http
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login?invalid-session=true")
                );*/

        return http.build();




    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }








}