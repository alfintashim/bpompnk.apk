package com.example.user.bpomproject.apihelper;

public class Grafik {

    public int bulan;
    public int jumlah;
    public int tahun;



    public Grafik(int input_bulan, int input_jumlah, int input_tahun){

        this.bulan = input_bulan;
        this.jumlah =input_jumlah;
        this.tahun = input_tahun;

    }

    public Grafik(){

    }


    public int getBulan() {
        return bulan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getTahun() {
        return tahun;
    }
}
