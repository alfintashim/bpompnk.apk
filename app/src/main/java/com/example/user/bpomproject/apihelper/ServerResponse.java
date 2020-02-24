package com.example.user.bpomproject.apihelper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServerResponse {
    @SerializedName("message")
    String message;

    @SerializedName("success")
    String success;

    @SerializedName("access_token")
    String access_token;

    @SerializedName("detail")
    List<Detail> detail;

    public int jumlah;

    public int getJumlah() {
        return jumlah;
    }

    public Users users;

    public Berita getBerita() {
        return berita;
    }

    public Berita berita;

    public Pengaduan pengaduan;

    public Biodata biodata;

    public Biodata getBiodata() {
        return biodata;
    }

    public Grafik grafik;

    public Pengaduan getPengaduan() {
        return pengaduan;
    }

    public Users getUsers() {
        return users;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAccess_token() {
        return access_token;
    }

    public List<Detail> getDetail() {
        return detail;
    }
}
