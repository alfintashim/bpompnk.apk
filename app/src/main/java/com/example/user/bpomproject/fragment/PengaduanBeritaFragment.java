package com.example.user.bpomproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.bpomproject.R;
import com.example.user.bpomproject.adapter.ListBeritaAdapter;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Berita;
import com.example.user.bpomproject.apihelper.Pengaduan;
import com.example.user.bpomproject.apihelper.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaduanBeritaFragment extends Fragment {
    @BindView(R.id.list_berita_rv)
    RecyclerView recyclerView;
    Context context;
    ListBeritaAdapter adapter;
    ApiService apiService;
    SharedPreferences sharedPreferences;
    String token;
    ImageView ivcariberita;
    EditText etcariberita;
    Boolean coba;
    List<Berita> beritas;
//    public static PengaduanBeritaFragment mi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pengaduan_berita, container, false);


        ButterKnife.bind(this,v);
        context = v.getContext();
        apiService = ApiClient.getApiService();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("token", "Nilai default");

//        mi =this;
        beritas = new ArrayList<Berita>();
        adapter = new ListBeritaAdapter(beritas,context);

        etcariberita = v.findViewById(R.id.etcariberita);

        coba = false;

        etcariberita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    listBerita();
                }else {
                    cariBerita(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listBerita();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listBerita();
    }

    public void listBerita()
    {
        Call<ServerResponse> call = apiService.list_berita("Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Berita berita;
                beritas.clear();

                if (resp != null){
                    for (int i=0; i<resp.getDetail().size(); i++){
                        int id = resp.getDetail().get(i).getId();
                        String name = resp.getDetail().get(i).getName();
                        String judul = resp.getDetail().get(i).getJudul();
                        String isi = resp.getDetail().get(i).getIsi();
                        String gambar = resp.getDetail().get(i).getGambar();
                        String status = resp.getDetail().get(i).getStatus();
                        int id_create = resp.getDetail().get(i).getId_create();
                        String created_at = resp.getDetail().get(i).getCreated_at();

                        berita = new Berita(id,name,judul,isi,gambar,status,id_create,created_at);
                        beritas.add(berita);
                    }
                }else {
                        Toast.makeText(context, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }


                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    public void cariBerita(String s)
    {
        Call<ServerResponse>call = apiService.cari_berita(s,"Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Berita berita;
                beritas.clear();

                if (resp != null){
                for (int i=0; i<resp.getDetail().size(); i++){
                    int id = resp.getDetail().get(i).getId();
                    String name = resp.getDetail().get(i).getName();
                    String judul = resp.getDetail().get(i).getJudul();
                    String isi = resp.getDetail().get(i).getIsi();
                    String gambar = resp.getDetail().get(i).getGambar();
                    String status = resp.getDetail().get(i).getStatus();
                    int id_create = resp.getDetail().get(i).getId_create();
                    String created_at = resp.getDetail().get(i).getCreated_at();

                    berita = new Berita(id,name,judul,isi,gambar,status,id_create,created_at);
                    beritas.add(berita);
                }

                }else {
                    Toast.makeText(context, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(context, ""+t, Toast.LENGTH_SHORT).show();
            }
        });
    }



}
