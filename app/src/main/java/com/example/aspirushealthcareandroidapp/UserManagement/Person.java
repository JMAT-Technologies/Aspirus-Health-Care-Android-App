package com.example.aspirushealthcareandroidapp.UserManagement;

public abstract class Person {
    private String userID;
    private String username;
    private String email;
    private String phone;
    private String dob;
    private int age;
    private String gender;
    private String password;

    public Person() {
    }

    public Person(String userID, String username, String email, String phone, String dob,int age, String gender, String password) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
