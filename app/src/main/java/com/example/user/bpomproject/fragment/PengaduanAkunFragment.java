package com.example.user.bpomproject.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.BantuanActivity;
import com.example.user.bpomproject.LoginActivity;
import com.example.user.bpomproject.PengaduanActivity;
import com.example.user.bpomproject.ProfilActivity;
import com.example.user.bpomproject.R;
import com.example.user.bpomproject.UbahPasswordActivity;
import com.example.user.bpomproject.UserAkunActivity;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaduanAkunFragment extends Fragment {

    TextView tvkatasandi,tvlogout,tvprofil,tvbantuan;
    ApiService apiService;
    String token;
    SharedPreferences sharedPreferences;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pengaduan_akun, container, false);

        apiService = ApiClient.getApiService();
        context = v.getContext();




        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = sharedPreferences.getString("token", null);


        tvkatasandi = v.findViewById(R.id.tvkatasandi);
        tvprofil = v.findViewById(R.id.tvprofil);
        tvlogout = v.findViewById(R.id.tvlogout);
        tvbantuan = v.findViewById(R.id.tvbantuan);

        tvbantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BantuanActivity.class);
                startActivity(intent);
            }
        });




        user();

        tvkatasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UbahPasswordActivity.class);
                startActivity(intent);
            }
        });

        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Anda Yakin Keluar?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                        startActivity(intent);

                        Toast.makeText(getContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialogBuilder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();

            }
        });


        return v;
    }

    private void user()
    {
        Call<Users> call = apiService.user("Bearer " + token ,"application/json");
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users resp = response.body();

                final int id = resp.getId();
                final String nama = resp.getName();


                tvprofil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent2 = new Intent(getContext(), ProfilActivity.class);
                        intent2.putExtra("nama",nama);
                        startActivity(intent2);
                    }
                });

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }



}
