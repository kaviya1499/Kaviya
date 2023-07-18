package com.atdxt.ControllerService;

import com.atdxt.Entity.Auth_Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SecurityUser implements UserDetails {
    private final Auth_Entity authEntity;

    public SecurityUser(Auth_Entity authEntity){
        this.authEntity=authEntity;
    }

    @Override
    public String getUsername(){
        return authEntity.getUsername();
    }

    @Override
    public String getPassword(){
        return authEntity.getPassword();
    }

   /* @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authEntity
                        .getRoles()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }*/
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return Collections.singleton(new SimpleGrantedAuthority("USER")); // Assuming all users have ROLE_USER
   }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
