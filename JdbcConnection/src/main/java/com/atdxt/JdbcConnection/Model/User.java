package com.atdxt.JdbcConnection.Model;


public class User {



    private int Id;
    private String Name;
    private String Age;
    private String Address;

    public User() {
    }

    public User(String name, String age, String address) {
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