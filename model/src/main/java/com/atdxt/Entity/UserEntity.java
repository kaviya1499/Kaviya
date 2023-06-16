package com.atdxt.Entity;


import jakarta.persistence.*;

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

    public UserEntity() {
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
    }
}
