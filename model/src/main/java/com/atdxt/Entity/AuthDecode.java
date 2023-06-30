package com.atdxt.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class AuthDecode {
    private String username;
    private String password;


    public AuthDecode(String username, String password) {
        this.username = username;
        this.password = password;

    }
}



