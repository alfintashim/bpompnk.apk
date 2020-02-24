package com.example.user.bpomproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.apihelper.Users;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.apihelper.Users;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahPasswordActivity extends AppCompatActivity {

    ImageView imageView;
    EditText etpassword_ubah,etpassword_confirm_ubah;
    Button btnubahpass;
    ApiService apiService;
    SharedPreferences sharedPreferences;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        apiService = ApiClient.getApiService();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "Nilai default");


        imageView = findViewById(R.id.ivbackinput4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etpassword_ubah = findViewById(R.id.etpassword_register_ubah);
        etpassword_confirm_ubah = findViewById(R.id.etpassword_confirm_ubah);
        btnubahpass = findViewById(R.id.btnubahpass);


        etpassword_confirm_ubah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String password = etpassword_ubah.getText().toString();

                if(s.toString().equals("")){
                    etpassword_confirm_ubah.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                }
                else if (!s.toString().equals(password)){
                    etpassword_confirm_ubah.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_failed_red,0);


                }else if(s.toString().equals(password))
                {
                    etpassword_confirm_ubah.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_check_green,0);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnubahpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubah();
            }
        });



    }


    private void ubah()
    {
        String password = etpassword_ubah.getText().toString();
        String password_confirm = etpassword_confirm_ubah.getText().toString();

        final Users users = new Users();
        users.setPassword(password);
        users.setPassword_confirmation(password_confirm);

        Call<ServerResponse> call = apiService.ganti_password(users,"Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                try {
                    ServerResponse resp = response.body();
                    Toast.makeText(UbahPasswordActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    etpassword_ubah.setText("");
                    etpassword_confirm_ubah.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UbahPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
}
