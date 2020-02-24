package com.example.user.bpomproject.apihelper;

public class Pengaduan {

    public int id;
    public String status;
    public int id_user;
    public String jenis_produk;
    public String nama_produk;
    public String no_reg;
    public String tgl_exp;
    public String nama_pabrik;
    public String alamat_pabrik;
    public String no_batch;
    public String lat;
    public String lng;
    public String alamat_beli;
    public String tgl_guna;
    public String info_lain;
    public String isi;
    public String jawaban;
    public int value;
    public int notif;
    public String file;
    public String created_at;
    public String nama;

    public Pengaduan( int input_id, String input_status, int input_id_user,String input_jenis_produk,
                      String input_nama_produk, String input_no_reg,String input_tgl_exp,String input_nama_pabrik,
                      String input_alamat_pabrik,String input_no_batch, String input_lat, String input_lng, String input_alamat_beli,
                      String input_tgl_guna,String input_info_lain,String input_isi, String input_file,String input_created_at, String input_nama,
                      String input_jawaban, int input_value,int input_notif){

        this.id = input_id;
        this.status =input_status;
        this.id_user = input_id_user;
        this.jenis_produk = input_jenis_produk;
        this.nama_produk = input_nama_produk;
        this.no_reg = input_no_reg;
        this.tgl_exp = input_tgl_exp;
        this.nama_pabrik = input_nama_pabrik;
        this.alamat_pabrik = input_alamat_pabrik;
        this.no_batch = input_no_batch;
        this.lat = input_lat;
        this.lng = input_lng;
        this.alamat_beli = input_alamat_beli;
        this.tgl_guna = input_tgl_guna;
        this.info_lain = input_info_lain;
        this.isi = input_isi;
        this.file = input_file;
        this.created_at = input_created_at;
        this.nama = input_nama;
        this.jawaban = input_jawaban;
        this.value = input_value;
        this.notif = input_notif;
    }

    public Pengaduan(){

    }

    public String getJawaban() {
        return jawaban;
    }

    public int getValue() {
        return value;
    }

    public int getNotif() {
        return notif;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getId_user() {
        return id_user;
    }

    public String getJenis_produk() {
        return jenis_produk;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setJenis_produk(String jenis_produk) {
        this.jenis_produk = jenis_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public void setNo_reg(String no_reg) {
        this.no_reg = no_reg;
    }

    public void setTgl_exp(String tgl_exp) {
        this.tgl_exp = tgl_exp;
    }

    public void setNama_pabrik(String nama_pabrik) {
        this.nama_pabrik = nama_pabrik;
    }

    public void setAlamat_pabrik(String alamat_pabrik) {
        this.alamat_pabrik = alamat_pabrik;
    }

    public void setNo_batch(String no_batch) {
        this.no_batch = no_batch;
    }


    public void setAlamat_beli(String alamat_beli) {
        this.alamat_beli = alamat_beli;
    }

    public void setTgl_guna(String tgl_guna) {
        this.tgl_guna = tgl_guna;
    }

    public void setInfo_lain(String info_lain) {
        this.info_lain = info_lain;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getNo_reg() {
        return no_reg;
    }



    public String getNama_pabrik() {
        return nama_pabrik;
    }

    public String getAlamat_pabrik() {
        return alamat_pabrik;
    }

    public String getNo_batch() {
        return no_batch;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAlamat_beli() {
        return alamat_beli;
    }

    public String getTgl_exp() {
        return tgl_exp;
    }

    public String getTgl_guna() {
        return tgl_guna;
    }

    public String getInfo_lain() {
        return info_lain;
    }

    public String getIsi() {
        return isi;
    }


    public String getNama() {
        return nama;
    }
}
