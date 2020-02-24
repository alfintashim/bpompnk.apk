package com.example.user.bpomproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.ServerResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IsiProfilActivity extends AppCompatActivity {

    ApiService apiService;
    String token;
    SharedPreferences sharedPreferences;
    Spinner etprofiljk;
    EditText etprofilnama,etprofilalamat,etprofilprofesi,etprofilinstansi,etprofilnohp,etprofilnoktp;
    Button btnprofiltambah,btnprofilreset;
    ImageView ivfoto,ivktp,ivisiprofil;
    Bitmap bitmap;
    Uri image_uri,image_uri2;
    String nama,jk,ktp,alamat,profesi,instansi,foto,no_ktp,no_hp;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_profil);

        apiService = ApiClient.getApiService();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "Nilai default");

         nama = this.getIntent().getStringExtra("nama");
         id = this.getIntent().getIntExtra("id",-1);
         jk = this.getIntent().getStringExtra("jk");
        ktp = this.getIntent().getStringExtra("ktp");
        alamat = this.getIntent().getStringExtra("alamat");
        profesi = this.getIntent().getStringExtra("profesi");
        instansi = this.getIntent().getStringExtra("instansi");
        foto = this.getIntent().getStringExtra("foto");
        no_ktp = this.getIntent().getStringExtra("no_ktp");
        no_hp = this.getIntent().getStringExtra("no_hp");

        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();



        etprofilnama = findViewById(R.id.etprofilnama);
        etprofiljk = findViewById(R.id.etprofiljk);
        etprofilnoktp = findViewById(R.id.etprofilnoktp);
        etprofilalamat = findViewById(R.id.etprofilalamat);
        etprofilprofesi = findViewById(R.id.etprofilprofesi);
        etprofilinstansi = findViewById(R.id.etprofilinstansi);
        etprofilnohp = findViewById(R.id.etprofilnohp);
        btnprofiltambah = findViewById(R.id.btntambahprofil);
//        ivfoto = findViewById(R.id.ivfoto);
        ivktp = findViewById(R.id.ivktp);
        ivisiprofil = findViewById(R.id.ivisiprofil);

        ivisiprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etprofilnama.setText(nama);
//        etprofilnama.setTextColor(Color.BLACK);
        etprofilnama.setEnabled(false);
        etprofilalamat.setText(alamat);
        etprofilprofesi.setText(profesi);
        etprofilinstansi.setText(instansi);
        etprofilnohp.setText(no_hp);
        etprofilnoktp.setText(no_ktp);
//        int spinnerPosition = adapter.getPosition(commpareValue);
//        etprofiljk.setSelection(((ArrayAdapter<String>)etprofiljk.getAdapter()).getPosition(jk));


        // Initializing a String Array
        String[] plants = new String[]{
                "Jenis Kelamin",
                "Laki-Laki",
                "Perempuan"
        };

        final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.colorBlackHint));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.colorDarkBlack));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        etprofiljk.setAdapter(spinnerArrayAdapter);
        ivktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening file chooser
                Intent i2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i2, 100);
            }
        });

        btnprofiltambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//

                String jk = etprofiljk.getSelectedItem().toString();
                String no_ktp = etprofilnoktp.getText().toString();
                String alamat = etprofilalamat.getText().toString();
                String profesi = etprofilprofesi.getText().toString();
                String instansi = etprofilinstansi.getText().toString();
                String no_hp = etprofilnohp.getText().toString();

            if (bitmap == null){
                Toast.makeText(IsiProfilActivity.this, "Upload KTP Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            else {
                if (jk.equals("")||no_ktp.equals("") || alamat.equals("")
                        || profesi.equals("") || instansi.equals("") || no_hp.equals("")){
                    Toast.makeText(IsiProfilActivity.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }  else {
                    uploadFile();
                }
            }


            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
//            //the image URI
//            image_uri = data.getData();
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image_uri);
//                ivfoto.setImageBitmap(bitmap);
//                ivfoto.setVisibility(View.VISIBLE);
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }else
            if(requestCode == 100 && resultCode == RESULT_OK && data != null) {

            image_uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),image_uri);
                ivktp.setImageBitmap(bitmap);
                ivktp.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFile(){
        String jk = etprofiljk.getSelectedItem().toString();
        String no_ktp = etprofilnoktp.getText().toString();
        String alamat = etprofilalamat.getText().toString();
        String profesi = etprofilprofesi.getText().toString();
        String instansi = etprofilinstansi.getText().toString();
        String no_hp = etprofilnohp.getText().toString();

        File file = getFileFromUri(this,image_uri);
//        File file2 = getFileFromUri(this,image_uri2);


        // Parsing any Media type file
        RequestBody reg_jk = RequestBody.create(MediaType.parse("multipart/form-data"), jk);
        RequestBody req_no_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), no_ktp);
        RequestBody req_alamat =RequestBody.create(MediaType.parse("multipart/form-data"), alamat);
        RequestBody req_profesi =RequestBody.create(MediaType.parse("multipart/form-data"), profesi);
        RequestBody req_instansi =RequestBody.create(MediaType.parse("multipart/form-data"), instansi);
        RequestBody req_no_hp =RequestBody.create(MediaType.parse("multipart/form-data"), no_hp);
//        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);

        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part ktp = MultipartBody.Part.createFormData("ktp",file.getName(), body);
//
//        RequestBody body2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
//        MultipartBody.Part foto = MultipartBody.Part.createFormData("foto",file.getName(), body2);


        Call<ServerResponse> call = apiService.biodata(id,reg_jk,req_no_ktp,req_alamat,req_profesi,req_instansi,req_no_hp,ktp,
                "Bearer " + token);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                Toast.makeText(IsiProfilActivity.this, ""+id, Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(), "Biodata Berhasil Di isi...", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(IsiProfilActivity.this, ProfilActivity.class);
                    intent2.putExtra("nama",nama);
                    startActivity(intent2);
//                    ivfoto.setImageResource(0);
//                    ivktp.setImageResource(0);
//                    etprofilnoktp.setText("");
//                    etprofilalamat.setText("");
//                    etprofilprofesi.setText("");
//                    etprofilinstansi.setText("");
//                    etprofilnohp.setText("");


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private File getFileFromUri(Context ctx, Uri uri){
        OutputStream out = null;
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String mimeType = mime.getExtensionFromMimeType(ctx.getContentResolver().getType(uri));
        File file = new File(getCacheDir(),"profile."+mimeType);
        InputStream in = null;
        try {
            in = ctx.getContentResolver().openInputStream(uri);
            out = new FileOutputStream(file);
            byte[] buf = new byte[2048];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if ( out != null ) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                in.close();
            }
            catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
