package com.atdxt.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@Entity
@Table(name="auth_table")
public class Auth_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;


    @Column(name="createdon")
    private String createdon;
    @Column(name="modified")
    private String modified;

    @Column(name="confirm_password")
    private String confirm_password;

    @Column(name = "roles")
    private String roles="USER";


    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;


    public Auth_Entity() {

    }

    public String getFormattedDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        createdon = currentDateTime.format(formatter);
        modified = currentDateTime.format(formatter);
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        modified = currentDateTime.format(formatter);
    }




    public Auth_Entity(String username, String decodedPassword, String createdon, String modified, String confirm_password, String roles) {
    }


}
