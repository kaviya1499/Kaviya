package com.atdxt.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@Entity
@Table(name= "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "Name")
    private String Name;
    @Column(name="Age")
    private String Age;
    @Column(name = "Address")
    private String Address;


    @Column(name = "createdon")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime createdon;

    @Column(name = "modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime modified;


    @JoinColumn(name = "detid")
    @OneToOne
    private Details_Entity detailsEntity;

    @JoinColumn(name = "auid")
    @OneToOne
    private Auth_Entity authEntity;








    public String getFormattedDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        createdon = LocalDateTime.parse(formattedDateTime, formatter);
        modified = LocalDateTime.parse(formattedDateTime, formatter);
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }

    public UserEntity() {
    }

    public UserEntity(UserRequest req) {
        this.Name = req.getName();
        this.Age = req.getAge();
        this.Address = req.getAddress();
    }


}