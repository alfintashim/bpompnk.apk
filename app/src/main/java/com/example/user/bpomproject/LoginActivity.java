package com.example.user.bpomproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.ServerRequest;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText etemail,etpass;
    TextView daftar;
    ProgressDialog loading;
    Context mContext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        daftar = findViewById(R.id.daftar);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        mContext = this;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = sharedPreferences.getString("token", null);
        int id_role = sharedPreferences.getInt("id_role", -1);

        if (token != null){
            switch (id_role) {
                case 4:
                    Intent intent = new Intent(LoginActivity.this, PengaduanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
            }
        }


        btnLogin.setOnClickListener(this);



        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent1);
            }
        });


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLogin:

                String email = etemail.getText().toString();
                String pass = etpass.getText().toString();

                if (email.equals("") || pass.equals("")) {
                    Toast.makeText(mContext, "Username atau Password Belum Di Isi", Toast.LENGTH_SHORT).show();
                } else {

                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                            .build();
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();
                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl("http://10.0.2.2:8000")
                            .baseUrl("http://bpomptk.web.id/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(okHttpClient)
                            .build();

                    //membuat objek webservice
                    final ApiService apiService = retrofit.create(ApiService.class);

                    ServerRequest dataLogin = new ServerRequest();
                    dataLogin.setEmail(email);
                    dataLogin.setPassword(pass);

                    final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Loading");
                    pd.show();
                    retrofit2.Call<ServerResponse> response = apiService.login(dataLogin);

                    response.enqueue(new Callback<ServerResponse>() {
                        @Override
                        public void onResponse(retrofit2.Call<ServerResponse> call, Response<ServerResponse> response) {

                            ServerResponse resp = response.body();
                            if (response.isSuccessful()) {

                                if (resp.getSuccess().equals("false")) {
                                    pd.dismiss();
                                    Toast.makeText(LoginActivity.this, "Username atau Password Salah.", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("token", resp.getAccess_token());
                                    editor.putInt("id_role", resp.users.id_role);
                                    editor.commit();


                                    int id_role = resp.getUsers().id_role;


                                    switch (id_role) {
                                        case 4:
                                            pd.dismiss();
                                            Intent intent = new Intent(LoginActivity.this, PengaduanActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
//                                    oneSignal();
                                            finish();
                                            break;
                                    }
                                }
                            }


                        }

                        @Override
                        public void onFailure(retrofit2.Call<ServerResponse> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(mContext, "Username atau Password Salah.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
}
