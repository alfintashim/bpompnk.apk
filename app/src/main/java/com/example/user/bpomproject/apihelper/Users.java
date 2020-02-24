package com.example.user.bpomproject.apihelper;

public class Users {

    public int id;
    public int id_role;
    public String name;
    public String username;
    public String password;
    public int active;
    public String email;
    public String password_confirmation;

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public int getId() {
        return id;
    }

    public int getId_role() {
        return id_role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
