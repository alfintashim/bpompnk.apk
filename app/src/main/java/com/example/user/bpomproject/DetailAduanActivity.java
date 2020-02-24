package com.example.user.bpomproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.adapter.ListBeritaAdapter;
import com.example.user.bpomproject.adapter.ListChatAdapter;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Berita;
import com.example.user.bpomproject.apihelper.Chat;
import com.example.user.bpomproject.apihelper.DateTime;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.fragment.PengaduanInputFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAduanActivity extends AppCompatActivity {
    @BindView(R.id.list_chat_rv)
    RecyclerView recyclerView;
    Context context;
    ListChatAdapter adapter;
    List<Chat> chats;
//    PDFView pdfView;
    WebView webView;
    TextView tvjenis_produk,tvtanggalpengaduan,tvstatusaduan,tvnamaproduk,tvalamatbeli;
    Button btnkomentar;
    EditText etkomentar,etjawaban;
    ImageView ivdetail,ivbackpengaduan;
    ApiService apiService;
    String token;
    int id,value,notif;
    FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    TextView txtjenis,txtnama, txtnoreg, txtkadarluasa, txtpabrik,txtalamat,txtnomor,txtguna,txtlokasi,txtlat,txtlng,txtinfo,txtisi,tvstatus;
    String jenis_produk,nama_produk, no_reg, tgl_exp, nama_pabrik,alamat_pabrik,alamat_beli,no_batch,tgl_guna,info_lain,isi,jawaban;
    String lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_aduan);



        ButterKnife.bind(this);
        context = this.getApplicationContext();
        apiService = ApiClient.getApiService();





        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailAduanActivity.this);
        token = sharedPreferences.getString("token", null);

        id = this.getIntent().getIntExtra("id",-1);
        final String status = this.getIntent().getStringExtra("status");
        final int id_user = this.getIntent().getIntExtra("id_user",-1);
        jenis_produk = this.getIntent().getStringExtra("jenis_produk");
        nama_produk = this.getIntent().getStringExtra("nama_produk");
        no_reg = this.getIntent().getStringExtra("no_reg");
        tgl_exp = this.getIntent().getStringExtra("tgl_exp");
        nama_pabrik = this.getIntent().getStringExtra("nama_pabrik");
        alamat_pabrik = this.getIntent().getStringExtra("alamat_pabrik");
        no_batch = this.getIntent().getStringExtra("no_batch");
        lat = this.getIntent().getStringExtra("lat");
        lng = this.getIntent().getStringExtra("lng");
        alamat_beli = this.getIntent().getStringExtra("alamat_beli");
        tgl_guna = this.getIntent().getStringExtra("tgl_guna");
        info_lain = this.getIntent().getStringExtra("info_lain");
        final String created_at = this.getIntent().getStringExtra("created_at");
        final String file = this.getIntent().getStringExtra("file");
        isi = this.getIntent().getStringExtra("isi");
        final String nama = this.getIntent().getStringExtra("nama");
        final String jawaban = this.getIntent().getStringExtra("jawaban");
        final int value = this.getIntent().getIntExtra("value",-1);
        final int notif = this.getIntent().getIntExtra("notif",-1);



//        pdfView = findViewById(R.id.pdfview);
        webView = findViewById(R.id.pdfview);
        btnkomentar = findViewById(R.id.btnkomentar);
        ivdetail = findViewById(R.id.ivdetail);
        ivbackpengaduan = findViewById(R.id.ivbackpengaduan);
        tvjenis_produk = findViewById(R.id.tvjenis_produk);
        tvtanggalpengaduan = findViewById(R.id.tvtanggalpengaduan);
        tvnamaproduk = findViewById(R.id.tvnamaproduk);
        tvstatusaduan = findViewById(R.id.tvstatusaduan);
        tvalamatbeli = findViewById(R.id.tvalamatbeli);
        tvnamaproduk.setText(nama_produk);

        txtjenis    = findViewById(R.id.txtjenis);
        txtnama    = findViewById(R.id.txtnama);
        txtnoreg   = findViewById(R.id.txtnoreg);
        txtkadarluasa   = findViewById(R.id.txtkadaluarsa);
        txtpabrik   = findViewById(R.id.txtpabrik);
        txtalamat   = findViewById(R.id.txtalamat);
        txtnomor   = findViewById(R.id.txtnomor);
        txtguna   = findViewById(R.id.txttanggal);
        txtlokasi   = findViewById(R.id.txtlokasi);
        txtlat   = findViewById(R.id.txtlat);
        txtlng   = findViewById(R.id.txtlng);
        txtinfo   = findViewById(R.id.txtinfo);
        txtisi   = findViewById(R.id.txtisi);
        tvstatus   = findViewById(R.id.tvstatus);
        etjawaban = findViewById(R.id.etjawaban);

//        String jenis_produk,nama_produk, no_reg, tgl_exp, nama_pabrik,alamat_pabrik,alamat,no_batch,alamat_beli,tgl_guna,info_lain,isi;
//        Double lat,lng;

        if (value != 0)
        {
            notif();
        }



        etjawaban.setText(jawaban);

        tvjenis_produk.setText(jenis_produk);
        tvtanggalpengaduan.setText(DateTime.getDateLong(created_at) + " Melaporkan");
        tvstatus.setText(status);

        if (status.equals("DITERIMA"))
        {
            tvstatusaduan.setText("T");
        }else if(status.equals("DIPROSES"))
        {
            tvstatusaduan.setText("P");
        }else if (status.equals("SELESAI"))
        {
            tvstatusaduan.setText("S");
        }

        tvalamatbeli.setText(alamat_beli);


        ivbackpengaduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listKomentar();



//        String pdf = "http://192.168.137.1:8000/storage/" + file;
//        pdfView.fromUri( "http://192.168.137.1:8000/storage/" + file)
//                .swipeHorizontal(true).load();

//        webView.setWebViewClient(new WebViewClient());

        String pdf = "http://bpomptk.web.id/storage/gambar/" + file;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ pdf);


        Button button = findViewById(R.id.btnwvaduan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlPDF = "http://bpomptk.web.id/storage/gambar/" + file;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPDF));
                startActivity(browserIntent);
            }
        });

        btnkomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahKomentar();
            }
        });



        ivdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DialogForm();
            }
        });




        chats = new ArrayList<Chat>();
        adapter = new ListChatAdapter(chats);




    }


    private void DialogForm() {
        dialog = new AlertDialog.Builder(DetailAduanActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_detail_aduan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Detail Aduan");

        txtjenis    = dialogView.findViewById(R.id.txtjenis);
        txtnama    = dialogView.findViewById(R.id.txtnama);
        txtnoreg   = dialogView.findViewById(R.id.txtnoreg);
        txtkadarluasa   = dialogView.findViewById(R.id.txtkadaluarsa);
        txtpabrik   = dialogView.findViewById(R.id.txtpabrik);
        txtalamat   = dialogView.findViewById(R.id.txtalamat);
        txtnomor   = dialogView.findViewById(R.id.txtnomor);
        txtguna   = dialogView.findViewById(R.id.txttanggal);
        txtlokasi   = dialogView.findViewById(R.id.txtlokasi);
        txtlat   = dialogView.findViewById(R.id.txtlat);
        txtlng   = dialogView.findViewById(R.id.txtlng);
        txtinfo   = dialogView.findViewById(R.id.txtinfo);
        txtisi   = dialogView.findViewById(R.id.txtisi);

//        String jenis_produk,nama_produk, no_reg, tgl_exp, nama_pabrik,alamat_pabrik,alamat,no_batch,alamat_beli,tgl_guna,info_lain,isi;
//        Double lat,lng;

        txtjenis.setText(jenis_produk);
        txtnama.setText(nama_produk);
        txtnoreg.setText(no_reg);
        txtkadarluasa.setText(tgl_exp);
        txtpabrik.setText(nama_pabrik);
        txtalamat.setText(alamat_pabrik);
        txtnomor.setText(no_batch);
        txtguna.setText(tgl_guna);
        txtlokasi.setText(alamat_beli);
        txtlat.setText(lat);
        txtlng.setText(lng);
        txtinfo.setText(info_lain);
        txtisi.setText(isi);






        dialog.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void listKomentar()
    {
        Call<ServerResponse> call = apiService.list_chat(id,"Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Chat chat;
                chats.clear();

                for (int i=0; i<resp.getDetail().size(); i++){
                    int id = resp.getDetail().get(i).getId();
                    int id_aduan = resp.getDetail().get(i).getId_aduan();
                    int id_user = resp.getDetail().get(i).getId_user();
                    int id_role = resp.getDetail().get(i).getId_role();
                    String pesan = resp.getDetail().get(i).getPesan();
                    String name = resp.getDetail().get(i).getName();
                    String created_at = resp.getDetail().get(i).getCreated_at();

                    chat = new Chat(id,id_aduan,id_user,id_role,pesan,name,created_at);
                    chats.add(chat);
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    private void tambahKomentar()
    {
        etkomentar = findViewById(R.id.etkomentar);

        String komentar = etkomentar.getText().toString();

        Chat chat = new Chat();
        chat.setPesan(komentar);


        Call<ServerResponse> call = apiService.komentar(id,chat,"Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Toast.makeText(DetailAduanActivity.this, "" +resp.getMessage(), Toast.LENGTH_SHORT).show();
                listKomentar();
                etkomentar.setText("");
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    private void notif()
    {
        Call<ServerResponse> call = apiService.update_notif(id,"Bearer " + token );
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }


}
