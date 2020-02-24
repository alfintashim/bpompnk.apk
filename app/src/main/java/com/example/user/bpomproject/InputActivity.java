package com.example.user.bpomproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.adapter.AduanScanAdapter;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Pengaduan;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.apihelper.Users;
import com.example.user.bpomproject.fragment.PengaduanAkunFragment;
import com.example.user.bpomproject.fragment.PengaduanBeritaFragment;
import com.example.user.bpomproject.fragment.PengaduanInputFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static butterknife.internal.Utils.arrayOf;

public class InputActivity extends AppCompatActivity {

    Spinner etjenisproduk;
    EditText etkadaluarsa,ettglguna,etlokasi,etlng,etlat,etnamaproduk,etreg,etnamapabrik,etalamatpabrik,etnomorbatch,etalamatbeli
            ,etinfotambahan,etisi;
    int OPEN_THING = 99;
    ArrayList<Bitmap> bitmaps;
    Button btnFoto,btnAlbum,btntambah,btnreset;
    RecyclerView rvFoto;
    private Calendar myCalendar;
    ApiService apiService;
    private Place place;
    int indexArrayList = 0;
    ImageView ivbackinput;
    String pesan;
    LayoutInflater inflater;
    View dialogView;
    String token;
    SharedPreferences sharedPreferences;
    public Double lat;
    ProgressDialog pd;
    public Double lng;
    protected PlaceDetectionClient mPlaceDetectionClient;
    private DatePickerDialog.OnDateSetListener date,date2;
    private int PLACE_PICKER_REQUEST = 11;
    Context context;
    Boolean cekbio = false;
    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    AduanScanAdapter adapter;
    public static InputActivity ma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        context = this;
        ma = this;

        apiService = ApiClient.getApiService();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "Nilai default");


        etjenisproduk = findViewById(R.id.etjenis);
        etnamaproduk = findViewById(R.id.etnamaproduk);
        etreg = findViewById(R.id.etreg);
        etnamapabrik = findViewById(R.id.etnamapabrik);
        etalamatpabrik = findViewById(R.id.etalamatpabrik);
        etnomorbatch = findViewById(R.id.etnomorbatch);
//        etalamatbeli = findViewById(R.id.etalamatbeli);
        etinfotambahan = findViewById(R.id.etinfotambahan);
        etisi = findViewById(R.id.etisi);
        etkadaluarsa = findViewById(R.id.etkadaluarsa);
        etlokasi = findViewById(R.id.etlokasi);
        etlng = findViewById(R.id.etlng);
        etlat = findViewById(R.id.etlat);
        ettglguna = findViewById(R.id.ettglguna);
        rvFoto = findViewById(R.id.rvFoto);
        btnFoto = findViewById(R.id.btnFoto);
        btnAlbum = findViewById(R.id.btnAlbum);
        btntambah = findViewById(R.id.btntambah);
        btnreset = findViewById(R.id.btnreset);


        bitmaps = new ArrayList<Bitmap>();
        adapter = new AduanScanAdapter(bitmaps);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                tanggalGuna();
            }
        };

        pd = new ProgressDialog(InputActivity.this);



        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaKamera();
            }
        });

        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaAlbum();
            }
        });

        ivbackinput = findViewById(R.id.ivbackinput);

        ivbackinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });



        // Initializing a String Array
        String[] plants = new String[]{
                "Jenis Produk",
                "Obat",
                "Alat Kesehatan",
                "Perbekalan Kesehatan Rumah Tangga",
                "Makanan-Minuman",
                "Obat Tradisional",
                "Kosmetik",
                "Suplemen Makanan",
                "Bahan Berbahaya",
                "Info Umum"
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
        etjenisproduk.setAdapter(spinnerArrayAdapter);


        getLocationPermission();

        etlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(InputActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                }
            }
        });

        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);


//        biodata();


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etnamaproduk.setText("");
                etreg.setText("");
                etnamapabrik.setText("");
                etalamatpabrik.setText("");
                etnomorbatch.setText("");
//                etalamatbeli.setText("");
                ettglguna.setText("");
                etinfotambahan.setText("");
                etisi.setText("");
                etlokasi.setText("");
                etlat.setText("");
                etlng.setText("");
                etkadaluarsa.setText("");

                if (bitmaps.size() == 0){
                    Toast.makeText(InputActivity.this, "Reset Data Input", Toast.LENGTH_SHORT).show();
                }else {
                    bitmaps.clear();
                    rvFoto.getAdapter().notifyDataSetChanged();
                }


            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jenis_produk = etjenisproduk.getSelectedItem().toString();
                String nama_produk = etnamaproduk.getText().toString();
                String no_reg = etreg.getText().toString();
                String tgl_exp = etkadaluarsa.getText().toString();
                String nama_pabrik = etnamapabrik.getText().toString();
                String alamat_pabrik = etalamatpabrik.getText().toString();
                String no_batch = etnomorbatch.getText().toString();
                String alamat_beli = etlokasi.getText().toString();
                String tgl_guna = ettglguna.getText().toString();
                String lat = etlat.getText().toString();
                String lng = etlng.getText().toString();
                String info_lain = etinfotambahan.getText().toString();
                String isi = etisi.getText().toString();





                    if (bitmaps.size() == 0){
                    Toast.makeText(InputActivity.this, "Scan Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    if (jenis_produk.equals("")|| nama_produk.equals("") || tgl_exp.equals("") || nama_pabrik.equals("")
                            || alamat_beli.equals("") || tgl_guna.equals("") || lat.equals("") || lng.equals("") || info_lain.equals("") || isi.equals(""))
                    {
                        Toast.makeText(InputActivity.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    }else {

                        View checkBoxView = View.inflate(InputActivity.this, R.layout.form_checkbox, null);
                        final CheckBox checkBox = checkBoxView.findViewById(R.id.checkbox);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                        alertDialogBuilder.setTitle("Kirim Aduan");
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.form_pasal, null);
                        alertDialogBuilder.setView(checkBoxView);
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkBox.isChecked()){
                                    new MyAsyncPDFAndUpload().execute();
                                }else {
                                    Toast.makeText(InputActivity.this, "Centang Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                        alertDialogBuilder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialogBuilder.show();

                        return;

                    }
                }




//                Toast.makeText(InputActivity.this, ""+ String.valueOf(bitmaps.size()), Toast.LENGTH_SHORT).show();
            }
        });


        etkadaluarsa.setOnClickListener(new View.OnClickListener() {
          @Override
           public void onClick(View v) {
              new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
          }
     });

    ettglguna.setOnClickListener(new View.OnClickListener() {
        @Override
       public void onClick(View v) {
            new DatePickerDialog(context, date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

   });
}

    @Override
    public void onResume() {
        super.onResume();
        biodata();
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());

        etkadaluarsa.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    private void tanggalGuna() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());

        ettglguna.setText(simpleDateFormat.format(myCalendar.getTime()));
    }


    private void bukaKamera()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 200);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }else {

            int preference = ScanConstants.OPEN_CAMERA;
            Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
            startActivityForResult(intent, OPEN_THING);
        }
    }

    private void bukaAlbum()
    {

        int preference = ScanConstants.OPEN_MEDIA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, OPEN_THING);
    }

    public void hapusScan(int position)
    {
        bitmaps.remove(position);
        refreshRecycler();


    }

    private void  refreshRecycler() {
        rvFoto.invalidate();
        rvFoto.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvFoto.setAdapter(adapter);
        rvFoto.setLayoutManager(layoutManager);
        rvFoto.smoothScrollToPosition(bitmaps.size()-1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
                Geocoder geocoder = new Geocoder(this);
                try
                {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude,place.getLatLng().longitude, 1);
//                    address = addresses.get(0).getAddressLine(0);
                    if (addresses != null && addresses.size() > 0) {
//                        kotaToko = addresses.get(0).getSubAdminArea();
                        lat = addresses.get(0).getLatitude();
                        lng = addresses.get(0).getLongitude();
                    }
                    //String country = addresses.get(0).getAddressLine(2);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                etlokasi.setText(place.getName());
                etlat.setText(String.valueOf(lat));
                etlng.setText(String.valueOf(lng));
//                placeId = place.getId();
               }
            }else if (requestCode == OPEN_THING  && resultCode == RESULT_OK) {
                Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    getContentResolver().delete(uri, null, null);

                    bitmaps.add(bitmap);
//                    bitmaps.set(indexArrayList,bitmap);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    rvFoto.setAdapter(adapter);
                    rvFoto.setLayoutManager(layoutManager);




//                        rvFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    @SuppressLint("SdCardPath")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private String saveToPDF() throws IOException, DocumentException {
        String targetPdf = "/sdcard/temp.pdf";
        File file = new File(targetPdf);
        OutputStream output = new FileOutputStream(file);
        //Step 1
        Document document = new Document();
        //Step 2
        PdfWriter.getInstance(document,output);

        //Step 3
        document.open();
        document.setPageSize(PageSize.A4);
        for (int i = 0; i < bitmaps.size() ;i++) {

            Bitmap bmp = bitmaps.get(i);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);
            Image image = Image.getInstance(baos.toByteArray());
//            image.setAlignment();
//
            int originalWidth = bmp.getWidth();
            int originalHeight = bmp.getHeight();
            Float resultWidth = PageSize.A4.getWidth();
            Float resultHeight = (originalHeight * resultWidth) / originalWidth;

            //image.setAbsolutePosition(0f,0f)
            image.setAbsolutePosition(0f, PageSize.A4.getHeight() - resultHeight);
            //image.scaleToFit(PageSize.A4.width, PageSize.A4.height)
//            image.scaleAbsolute(PageSize.A4.width, PageSize.A4.height)
            image.scaleToFit(PageSize.A4.getWidth(), resultHeight);
            document.add(image);
            document.newPage();
        }

        document.close();
        output.close();
        return "PDF berhasil dibuat";

    }

    @SuppressLint("SdCardPath")
    private void tambahAduan()
    {

        String Pdfpath = "/sdcard/temp.pdf";
        File file = new File(Pdfpath);

        if (!file.exists()){
            Toast.makeText(this, "File PDF Belum terbentuk", Toast.LENGTH_LONG).show();
        }else {
            pd.setMessage("Mengirim data...");
            pd.show();

            String jenis_produk = etjenisproduk.getSelectedItem().toString();
            String nama_produk = etnamaproduk.getText().toString();
            String no_reg = etreg.getText().toString();
            String tgl_exp = etkadaluarsa.getText().toString();
            String nama_pabrik = etnamapabrik.getText().toString();
            String alamat_pabrik = etalamatpabrik.getText().toString();
            String no_batch = etnomorbatch.getText().toString();
            String alamat_beli = etlokasi.getText().toString();
            String tgl_guna = ettglguna.getText().toString();
            String lat = etlat.getText().toString();
            String lng = etlng.getText().toString();
            String info_lain = etinfotambahan.getText().toString();
            String isi = etisi.getText().toString();



            // Parsing any Media type file
            RequestBody reg_jenis_produk = RequestBody.create(MediaType.parse("multipart/form-data"), jenis_produk);
            RequestBody req_nama_produk = RequestBody.create(MediaType.parse("multipart/form-data"), nama_produk);
            RequestBody req_nomor_reg =RequestBody.create(MediaType.parse("multipart/form-data"), no_reg);
            RequestBody req_tgl_exp =RequestBody.create(MediaType.parse("multipart/form-data"), tgl_exp);
            RequestBody req_nama_pabrik =RequestBody.create(MediaType.parse("multipart/form-data"), nama_pabrik);
            RequestBody req_alamat_pabrik =RequestBody.create(MediaType.parse("multipart/form-data"), alamat_pabrik);
            RequestBody req_nomor_batch =RequestBody.create(MediaType.parse("multipart/form-data"), no_batch);
            RequestBody req_alamat_beli =RequestBody.create(MediaType.parse("multipart/form-data"),alamat_beli);
            RequestBody req_tgl_guna =RequestBody.create(MediaType.parse("multipart/form-data"), tgl_guna);
//        RequestBody lokasi =RequestBody.create(MediaType.parse("text/plain"), etlokasi.getText().toString());
            RequestBody req_lat =RequestBody.create(MediaType.parse("multipart/form-data"), lat);
            RequestBody req_lng =RequestBody.create(MediaType.parse("multipart/form-data"),lng);
            RequestBody req_info_lain =RequestBody.create(MediaType.parse("multipart/form-data"), info_lain);
            RequestBody req_isi =RequestBody.create(MediaType.parse("multipart/form-data"), isi);


            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",file.getName(), body);


            Call<ServerResponse> call = apiService.aduan(reg_jenis_produk,req_nama_produk,req_nomor_reg,
                    req_tgl_exp,req_nama_pabrik,req_alamat_pabrik,req_nomor_batch,req_alamat_beli,req_tgl_guna,req_lat,req_lng,req_info_lain
                    ,req_isi,filePart,"Bearer " + token);

            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()){
                        pd.hide();
                        ServerResponse resp = response.body();
                        Toast.makeText(InputActivity.this, ""+resp.getMessage(), Toast.LENGTH_SHORT).show();
//                        etjenisproduk.setAdapter(null);
                       finish();
                    }else {
                        pd.hide();

                        Toast.makeText(InputActivity.this, "Respon Gagal", Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    pd.hide();
                    Toast.makeText(InputActivity.this, "Terjadi masalah saat mengirim surat" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    @SuppressLint("StaticFieldLeak")
    public class MyAsyncPDFAndUpload extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Konversi Gambar ke PDF.....");
            pd.show();

        }

        @SuppressLint("SdCardPath")
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String message = "";

            try {
               message = saveToPDF();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            return message;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            tambahAduan();
        }
    }







    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Menangani hasil request location permission.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;

                }
            }
        }
    }

    private void biodata()
    {
        Call<ServerResponse> call = apiService.aktivasi_biodata("Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                if (resp.getMessage().equals("Tidak Ada")){

                    Intent intent = new Intent(getApplicationContext(), PengaduanActivity.class);
                    startActivity(intent);
                    Toast.makeText(InputActivity.this, "Isi Data Profil Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InputActivity.this, "Silahkan input aduan....", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

}
