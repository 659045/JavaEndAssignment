package com.example.javaendassignment;

import java.util.Date;

public class User {
    public int id;
    public String username;
    public String password;
    public Date birthday;

    public User(int id, String username, String password, Date birthday) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }
}
