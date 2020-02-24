package com.example.user.bpomproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.bpomproject.fragment.PengaduanBeritaFragment;

public class DetailBeritaActivity extends AppCompatActivity {

    TextView judul_berita,tanggal_berita,isi_berita;
    ImageView gambar_berita,ivbackberita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailBeritaActivity.this);
        String token = sharedPreferences.getString("token", null);

        final int id = this.getIntent().getIntExtra("id",-1);
        final String judul = this.getIntent().getStringExtra("judul");
        final int id_create = this.getIntent().getIntExtra("id_create",-1);
        final String gambar = this.getIntent().getStringExtra("gambar");
        final String isi = this.getIntent().getStringExtra("isi");
        final String status = this.getIntent().getStringExtra("status");
        final String name = this.getIntent().getStringExtra("name");
        final String created_at = this.getIntent().getStringExtra("created_at");

        ivbackberita = findViewById(R.id.ivbackberita);
        ivbackberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });




        judul_berita = findViewById(R.id.judul_berita);
        tanggal_berita =findViewById(R.id.tanggal_berita);
        gambar_berita = findViewById(R.id.gambar_berita);
        isi_berita = findViewById(R.id.isi_berita);



        judul_berita.setText(judul);
        isi_berita.setText(isi);
        tanggal_berita.setText(created_at);

        Glide
                .with(getApplicationContext())
                .load("http://192.168.137.1:8000/storage/"+gambar)
                .into(gambar_berita);









    }
}
