package com.example.user.bpomproject.apihelper;

public class Berita {
    public int id;
    public String name;
    public String judul;
    public String isi;
    public String gambar;
    public String status;
    public int id_create;
    public String created_at;


    public Berita(int input_id, String input_name, String input_judul, String input_isi,
                  String input_gambar, String input_status, int input_id_create, String input_created_at){

        this.id = input_id;
        this.name =input_name;
        this.judul = input_judul;
        this.isi = input_isi;
        this.gambar = input_gambar;
        this.status = input_status;
        this.id_create = input_id_create;
        this.created_at = input_created_at;

    }

    public Berita(){

    }
    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }

    public int getId_create() {
        return id_create;
    }

    public String getGambar() {
        return gambar;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getCreated_at() {
        return created_at;
    }
}
