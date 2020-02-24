package com.example.user.bpomproject.apihelper;

import com.google.gson.annotations.SerializedName;

public class ServerRequest {
    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
