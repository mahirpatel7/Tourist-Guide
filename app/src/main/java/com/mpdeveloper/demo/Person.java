package com.mpdeveloper.demo;

public class Person {
    private String userId;
    private String email;
    private String password;

    public Person() {
        // Default constructor required for calls to DataSnapshot.getValue(Person.class)
    }

    public Person(String userId,String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
