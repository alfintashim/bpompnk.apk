package com.example.user.bpomproject.apihelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/auth/login")
    Call<ServerResponse> login(
            @Body ServerRequest dataLogin
    );

    @Multipart
    @POST("api/auth/signup")
    Call<ServerResponse> register(
            @Part("name") RequestBody name,
            @Part("username") RequestBody username,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirm") RequestBody password_confirm
    );

    @GET("api/auth/user")
    Call<Users> user(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @Multipart
    @POST("api/auth/aduan_masuk")
    Call<ServerResponse> aduan(
            @Part("jenis_produk") RequestBody jenis_produk,
            @Part("nama_produk") RequestBody nama_produk,
            @Part("no_reg") RequestBody no_reg,
            @Part("tgl_exp") RequestBody tgl_exp,
            @Part("nama_pabrik") RequestBody nama_pabrik,
            @Part("alamat_pabrik") RequestBody alamat_pabrik,
            @Part("no_batch") RequestBody no_batch,
            @Part("alamat_beli") RequestBody alamat_beli,
            @Part("tgl_guna") RequestBody tgl_guna,
            @Part("lat") RequestBody lat,
            @Part("lng") RequestBody lng,
            @Part("info_lain") RequestBody info_lain,
            @Part("isi") RequestBody isi,
            @Part MultipartBody.Part file,
            @Header("Authorization") String authHeader
//            @Header("Content-Type") String contentType

    );

    @Multipart
    @POST("api/auth/biodata/{id}")
    Call<ServerResponse> biodata(
            @Path("id") int id,
            @Part("jk") RequestBody jk,
            @Part("no_ktp") RequestBody no_ktp,
            @Part("alamat") RequestBody alamat,
            @Part("profesi") RequestBody profesi,
            @Part("instansi") RequestBody instansi,
            @Part("no_hp") RequestBody no_hp,
            @Part MultipartBody.Part ktp,
//            @Part MultipartBody.Part foto,
            @Header("Authorization") String authHeader
//            @Header("Content-Type") String contentType

    );

    @POST("api/auth/pesan/{id_aduan}")
    Call<ServerResponse> komentar(
            @Path("id_aduan") int id_aduan,
            @Body Chat chat,
            @Header("Authorization") String authHeader,
            @Header("Content-Type") String contentType
    );

    @GET("api/auth/update_notif/{id}")
    Call<ServerResponse> update_notif(
            @Path("id") int id,
            @Header("Authorization") String authHeader
    );

    @GET("api/auth/list_berita")
    Call<ServerResponse> list_berita(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @GET("api/auth/detail_biodata")
    Call<Biodata> detail_biodata(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @GET("api/auth/aktivasi_biodata")
    Call<ServerResponse> aktivasi_biodata(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @GET("api/auth/cari_berita/{search}")
    Call<ServerResponse> cari_berita(@Path("search") String judul,
                                     @Header("Authorization") String authHeader,
                                     @Header("Content-Type") String contentType);

    @GET("api/auth/list_dash_berita")
    Call<ServerResponse> list_dash_berita(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @GET("api/auth/list_aduan")
    Call<ServerResponse> list_aduan(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @GET("api/auth/cari_aduan/{search}")
    Call<ServerResponse> cari_aduan(@Path("search") String nama_produk,
                                    @Header("Authorization") String authHeader,
                                    @Header("Content-Type") String contentType);

    @GET("api/auth/user_dahsboard")
    Call<ServerResponse> dashboard(@Header("Authorization") String authHeader,@Header("Content-Type") String contentType);

    @GET("api/auth/list_chat/{id_aduan}")
    Call<ServerResponse> list_chat(@Path("id_aduan") int id_aduan,
                                   @Header("Authorization") String authHeader,
                                   @Header("Content-Type") String contentType);


    @POST("api/auth/ganti_password")
    Call<ServerResponse> ganti_password(
            @Body Users users,
            @Header("Authorization") String authHeader,
            @Header("Content-Type") String contentType
    );


    @POST("api/email_register")
    Call<ServerResponse> email_register(
            @Query("email") String email
    );

    @POST("api/username_register")
    Call<ServerResponse> username_register(
            @Body Users users
    );

    @GET("api/auth/notif")
    Call<ServerResponse> notif(@Header("Authorization") String authHeader);




}
