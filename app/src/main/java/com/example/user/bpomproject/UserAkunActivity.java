package com.example.user.bpomproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.apihelper.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAkunActivity extends AppCompatActivity {

////    ImageView ivbackinput3;
//    TextView tvkatasandi,tvlogout,tvprofil;
//    ApiService apiService;
//    String token;
//    SharedPreferences sharedPreferences;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_akun);
//
//        apiService = ApiClient.getApiService();
//
//
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UserAkunActivity.this);
//        token = sharedPreferences.getString("token", null);
//
//        user();
//
////        ivbackinput3 = findViewById(R.id.ivbackinput3);
//        tvkatasandi = findViewById(R.id.tvkatasandi);
//        tvprofil = findViewById(R.id.tvprofil);
//        tvlogout = findViewById(R.id.tvlogout);
////        ivbackinput3.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                Intent intent = new Intent(getApplicationContext(), PengaduanActivity.class);
////                startActivity(intent);
////
////            }
////        });
//
//
//
//        tvkatasandi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserAkunActivity.this,UbahPasswordActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        tvlogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
//                alertDialogBuilder.setMessage("Anda Yakin Keluar?");
//                alertDialogBuilder.setCancelable(false);
//                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.clear();
//                        editor.commit();
//
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//
//                        startActivity(intent);
//
//                        Toast.makeText(getApplicationContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                alertDialogBuilder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alertDialogBuilder.show();
//
//            }
//        });
    }


//    private void user()
//    {
//        Call<Users> call = apiService.user("Bearer " + token ,"application/json");
//        call.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
//                Users resp = response.body();
//
//                final int id = resp.getId();
//                final String nama = resp.getName();
//
//
//                tvprofil.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent2 = new Intent(UserAkunActivity.this,ProfilActivity.class);
//                        intent2.putExtra("nama",nama);
//                        startActivity(intent2);
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Call<Users> call, Throwable t) {
//
//            }
//        });
//    }


}
