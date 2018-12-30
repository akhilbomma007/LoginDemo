package com.example.prithvi.logindemo;

public class UserProfile {

    private String age;
    private String email;
    private String name;

    public UserProfile() {
    }

    public UserProfile(String age, String email, String name) {
        this.age = age;
        this.email = email;
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
