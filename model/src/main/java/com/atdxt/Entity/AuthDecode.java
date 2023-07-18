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
    private String createdon;
    private String modified;
    private String confirm_password;


    public AuthDecode(String username, String password, String createdon, String modified, String confirm_password) {
        this.username = username;
        this.password = password;
        this.createdon= createdon;
        this.modified= modified;
        this.confirm_password=confirm_password;

    }
}



