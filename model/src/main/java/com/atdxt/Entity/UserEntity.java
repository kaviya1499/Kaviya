package com.atdxt.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime createdon;

    @Column(name = "modified")
    private LocalDateTime modified;


    @PrePersist
    protected void onCreate() {
        createdon = LocalDateTime.now();
        modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }

   /* public UserEntity() {
    }

    public UserEntity(String name, String age, String address) {
        this.Name = name;
        this.Age = age;
        this.Address = address;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }*/
}
