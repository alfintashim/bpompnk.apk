package com.example.user.bpomproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.apihelper.Users;
import com.example.user.bpomproject.apihelper.ApiService;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {


    ApiService apiService;
    EditText etnama,etusername,etemail,etpassword,etpassword_confirm;
    Button daftartekan;
    Boolean emailCoba = false;
    Boolean usernameCoba = false;
    Boolean passwordCoba = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiService = ApiClient.getApiService();

        etnama = findViewById(R.id.etnama);
        etusername = findViewById(R.id.etusername);
        etemail = findViewById(R.id.etemailregister);
        etpassword = findViewById(R.id.etpassword_register);
        daftartekan = findViewById(R.id.btnRegister);
        etpassword_confirm = findViewById(R.id.etpassword_confirm);


        etemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cekEmail();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cekUsername();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etpassword_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String password = etpassword.getText().toString();

                if(s.toString().equals("")){
                    etpassword_confirm.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
                else if (!s.toString().equals(password)){
                    etpassword_confirm.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_failed_red,0);


                }else if(s.toString().equals(password))
                {
                    etpassword_confirm.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_green,0);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        daftartekan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daftar();
            }
        });



    }


    private void cekEmail(){

        final String email = etemail.getText().toString();



        Call<ServerResponse> call = apiService.email_register(email);


        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(etemail.equals("")){
                    etemail.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
                else if (resp.getMessage().equals("Ada")){
                    etemail.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_failed_red,0);

                    emailCoba = true;

                }else if(resp.getMessage().equals("Tidak Ada"))
                {
                    etemail.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_green,0);

                    emailCoba = false;
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });


    }

    private void cekUsername(){

        final String username = etusername.getText().toString();

        Users username_register =new Users();
        username_register.setUsername(username);

        Call<ServerResponse> call = apiService.username_register(username_register);



        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(etusername.equals("")){
                    etusername.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
                else if (resp.getMessage().equals("Ada")){
                    etusername.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_failed_red,0);

                    usernameCoba = true;

                }else if(resp.getMessage().equals("Tidak Ada"))
                {
                    etusername.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_green,0);

                    usernameCoba = false;
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });


    }

    public void daftar(){
        String name = etnama.getText().toString();
        String username = etusername.getText().toString();
        String email = etemail.getText().toString();
        String password = etpassword.getText().toString();
        String password_confirm = etpassword_confirm.getText().toString();

        RequestBody reg_name = RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody reg_username = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody reg_email =RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody reg_password =RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody reg_password_confirm =RequestBody.create(MediaType.parse("multipart/form-data"), password_confirm);

        Call<ServerResponse> call = apiService.register(reg_name,reg_username,reg_email,reg_password,reg_password_confirm);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, AktivasiActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });

    }
}
