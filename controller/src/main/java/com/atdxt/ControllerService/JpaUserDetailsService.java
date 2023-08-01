package com.atdxt.ControllerService;

import com.atdxt.Entity.Auth_Entity;
import com.atdxt.RepositoryService.AuthRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;
    public JpaUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);
        return authRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
    }

}
class SecurityUser implements UserDetails {
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
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
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
