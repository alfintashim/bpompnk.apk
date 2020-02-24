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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.InputActivity;
import com.example.user.bpomproject.R;
import com.example.user.bpomproject.adapter.ListAduanAdapter;
import com.example.user.bpomproject.adapter.ListBeritaAdapter;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Berita;
import com.example.user.bpomproject.apihelper.Pengaduan;
import com.example.user.bpomproject.apihelper.ServerResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaduanInputFragment extends Fragment {

    ImageView imageView,ivcariaduan;
    @BindView(R.id.list_pengaduan_rv)
    RecyclerView recyclerView;
    Context context;
    EditText etcariaduan;
    String token;
    ListAduanAdapter adapter;
    ApiService apiService;
    List<Pengaduan> pengaduans;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pengaduan_input, container, false);


        ButterKnife.bind(this,v);
        context = v.getContext();
         apiService = ApiClient.getApiService();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("token", "Nilai default");

        ivcariaduan = v.findViewById(R.id.ivcariaduan);
        etcariaduan = v.findViewById(R.id.etcariaduan);



        pengaduans = new ArrayList<Pengaduan>();
        adapter = new ListAduanAdapter(pengaduans);

        list();

        etcariaduan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    list();
                }else {
                    cari(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        imageView = v.findViewById(R.id.input_form_pengaduan);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),InputActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        list();
    }

    public void list(){
        Call<ServerResponse> call = apiService.list_aduan("Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Pengaduan pengaduan;
                pengaduans.clear();

                for (int i=0; i<resp.getDetail().size(); i++){
                    int id = resp.getDetail().get(i).getId();
                    String status = resp.getDetail().get(i).getStatus();
                    int id_user = resp.getDetail().get(i).getId_user();
                    String jenis_produk = resp.getDetail().get(i).getJenis_produk();
                    String nama_produk = resp.getDetail().get(i).getNama_produk();
                    String no_reg = resp.getDetail().get(i).getNo_reg();
                    String tgl_exp = resp.getDetail().get(i).getTgl_exp();
                    String nama_pabrik = resp.getDetail().get(i).getNama_pabrik();
                    String alamat_pabrik = resp.getDetail().get(i).getAlamat_pabrik();
                    String no_batch = resp.getDetail().get(i).getNo_batch();
                    String lat = resp.getDetail().get(i).getLat();
                    String lng = resp.getDetail().get(i).getLng();
                    String alamat_beli = resp.getDetail().get(i).getAlamat_beli();
                    String tgl_guna = resp.getDetail().get(i).getTgl_guna();
                    String info_lain = resp.getDetail().get(i).getInfo_lain();
                    String isi = resp.getDetail().get(i).getIsi();
                    String file = resp.getDetail().get(i).getFile();
                    String created_at = resp.getDetail().get(i).getCreated_at();
                    String nama = resp.getDetail().get(i).getNama();
                    String jawaban = resp.getDetail().get(i).getJawaban();
                    int value = resp.getDetail().get(i).getValue();
                    int notif = resp.getDetail().get(i).getNotif();



                    pengaduan = new Pengaduan(id,status,id_user,jenis_produk,nama_produk,no_reg,tgl_exp
                            ,nama_pabrik,alamat_pabrik,no_batch,lat,lng,alamat_beli,tgl_guna,info_lain,isi,file,created_at,nama,jawaban,
                            value,notif);
                    pengaduans.add(pengaduan);
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

    public void cari(String s)
    {
        Call<ServerResponse>call = apiService.cari_aduan(s,"Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                    Pengaduan pengaduan;
                    pengaduans.clear();
                    if (resp != null){
                        for (int i=0; i<resp.getDetail().size(); i++){
                            int id = resp.getDetail().get(i).getId();
                            String status = resp.getDetail().get(i).getStatus();
                            int id_user = resp.getDetail().get(i).getId_user();
                            String jenis_produk = resp.getDetail().get(i).getJenis_produk();
                            String nama_produk = resp.getDetail().get(i).getNama_produk();
                            String no_reg = resp.getDetail().get(i).getNo_reg();
                            String tgl_exp = resp.getDetail().get(i).getTgl_exp();
                            String nama_pabrik = resp.getDetail().get(i).getNama_pabrik();
                            String alamat_pabrik = resp.getDetail().get(i).getAlamat_pabrik();
                            String no_batch = resp.getDetail().get(i).getNo_batch();
                            String lat = resp.getDetail().get(i).getLat();
                            String lng = resp.getDetail().get(i).getLng();
                            String alamat_beli = resp.getDetail().get(i).getAlamat_beli();
                            String tgl_guna = resp.getDetail().get(i).getTgl_guna();
                            String info_lain = resp.getDetail().get(i).getInfo_lain();
                            String isi = resp.getDetail().get(i).getIsi();
                            String file = resp.getDetail().get(i).getFile();
                            String created_at = resp.getDetail().get(i).getCreated_at();
                            String nama = resp.getDetail().get(i).getNama();
                            String jawaban = resp.getDetail().get(i).getJawaban();
                            int value = resp.getDetail().get(i).getValue();
                            int notif = resp.getDetail().get(i).getNotif();


                            pengaduan = new Pengaduan(id,status,id_user,jenis_produk,nama_produk,no_reg,tgl_exp
                                    ,nama_pabrik,alamat_pabrik,no_batch,lat,lng,alamat_beli,tgl_guna,info_lain,isi,file,created_at,nama,jawaban,
                                    value,notif);
                            pengaduans.add(pengaduan);
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
