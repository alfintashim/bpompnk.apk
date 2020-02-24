package com.example.user.bpomproject.apihelper;

public class Chat {
    public int id;
    public int id_aduan;
    public int id_user;
    public int id_role;
    public String pesan;
    public String name;
    public String created_at;

    public Chat(int input_id, int input_id_aduan, int input_id_user, int input_id_role,
                String input_pesan,String input_name, String input_created_at){

        this.id = input_id;
        this.id_aduan =input_id_aduan;
        this.id_user = input_id_user;
        this.id_role = input_id_role;
        this.pesan = input_pesan;
        this.name = input_name;
        this.created_at = input_created_at;

    }

    public Chat(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getId_aduan() {
        return id_aduan;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_role() {
        return id_role;
    }

    public String getPesan() {
        return pesan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_aduan(int id_aduan) {
        this.id_aduan = id_aduan;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
