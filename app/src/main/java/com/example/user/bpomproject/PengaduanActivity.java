package com.example.user.bpomproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.user.bpomproject.fragment.PengaduanAkunFragment;
import com.example.user.bpomproject.fragment.PengaduanBeritaFragment;
import com.example.user.bpomproject.fragment.PengaduanDashboardFragment;
import com.example.user.bpomproject.fragment.PengaduanInputFragment;

public class PengaduanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PengaduanActivity.this);
        String token = sharedPreferences.getString("token", "Nilai default");

        BottomNavigationView bottomNavigationView = findViewById(R.id.navPengaduan);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.framPengaduan,
                new PengaduanDashboardFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.navBeranda:
                            selectedFragment = new PengaduanDashboardFragment();
                            break;
                        case R.id.navInput:
                            selectedFragment = new PengaduanInputFragment();
                            break;
                        case R.id.navBerita:
                            selectedFragment = new PengaduanBeritaFragment();
                            break;
                        case R.id.navAkun:
                            selectedFragment = new PengaduanAkunFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.framPengaduan, selectedFragment).commit();
                    return true;
                }
            };



    }

