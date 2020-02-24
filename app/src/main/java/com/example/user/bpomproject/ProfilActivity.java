package com.example.user.bpomproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Biodata;
import com.example.user.bpomproject.apihelper.ServerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    Button isiprofil;
    TextView namaprofil,ktpprofil,alamatprofil,profesiprofil,jkprofil,instansiprofil,hpprofil;
    ApiService apiService;
    String token,jk,ktp,alamat,profesi,instansi,foto,no_ktp,no_hp;
    ImageView fototampil,ktptampil,ivprofil;
    SharedPreferences sharedPreferences;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        apiService = ApiClient.getApiService();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilActivity.this);
        token = sharedPreferences.getString("token", null);

        final String nama = this.getIntent().getStringExtra("nama");



        isiprofil = findViewById(R.id.isiprofil);
        namaprofil = findViewById(R.id.namaprofil);
        ktpprofil = findViewById(R.id.ktpprofil);
        alamatprofil = findViewById(R.id.alamatprofil);
        profesiprofil = findViewById(R.id.profesiprofil);
        jkprofil = findViewById(R.id.jkprofil);
        instansiprofil = findViewById(R.id.instansiprofil);
        hpprofil = findViewById(R.id.hpprofil);
//        fototampil = findViewById(R.id.fototampil);
        ktptampil = findViewById(R.id.ktptampil);
        ivprofil = findViewById(R.id.ivprofil);

        ivprofil = findViewById(R.id.ivprofil);

        ivprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(ProfilActivity.this,PengaduanActivity.class);
//                intent2.putExtra("nama",nama);
                startActivity(intent3);
            }
        });

        isiprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProfilActivity.this,IsiProfilActivity.class);
                intent2.putExtra("nama",nama);
                intent2.putExtra("id",id);
                intent2.putExtra("no_ktp",no_ktp);
                intent2.putExtra("alamat",alamat);
                intent2.putExtra("profesi",profesi);
                intent2.putExtra("jk",jk);
                intent2.putExtra("instansi",instansi);
                intent2.putExtra("no_hp",no_hp);
                startActivity(intent2);
            }
        });


        namaprofil.setText(nama);


        bio();



    }

    private void bio()
    {
        Call<Biodata> call = apiService.detail_biodata("Bearer " + token ,"application/json");
        call.enqueue(new Callback<Biodata>() {
            @Override
            public void onResponse(Call<Biodata> call, Response<Biodata> response) {

                if (ktpprofil.equals("") || alamatprofil.equals("") || profesiprofil.equals("") || jkprofil.equals("")
                        || instansiprofil.equals("") || hpprofil.equals("")) {
                    Toast.makeText(ProfilActivity.this, "Isi Data Profil Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    Biodata resp = response.body();
                    id = resp.getId();
                     jk = resp.getJk();
                     ktp = resp.getKtp();
                     alamat = resp.getAlamat();
                     profesi = resp.getProfesi();
                     instansi = resp.getInstansi();
                     foto = resp.getFoto();
                     no_ktp = resp.getNo_ktp();
                     no_hp = resp.getNo_hp();

                    ktpprofil.setText(no_ktp);
                    alamatprofil.setText(alamat);
                    profesiprofil.setText(profesi);
                    jkprofil.setText(jk);
                    instansiprofil.setText(instansi);
                    hpprofil.setText(no_hp);

//                    Glide
//                            .with(getApplicationContext())
//                            .load("http://bpomptk.web.id/storage/foto/"+foto)
//                            .into(fototampil);

                    Glide
                            .with(getApplicationContext())
                            .load("http://bpomptk.web.id/storage/profil/"+ktp)
                            .into(ktptampil);
                }





            }

            @Override
            public void onFailure(Call<Biodata> call, Throwable t) {

            }
        });
    }
}
