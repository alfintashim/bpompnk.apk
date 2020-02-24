package com.example.user.bpomproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bpomproject.InputActivity;
import com.example.user.bpomproject.R;
import com.example.user.bpomproject.UserAkunActivity;
import com.example.user.bpomproject.adapter.ListBeritaAdapter;
import com.example.user.bpomproject.adapter.ListDashboardBerita;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Berita;
import com.example.user.bpomproject.apihelper.Detail;
import com.example.user.bpomproject.apihelper.Grafik;
import com.example.user.bpomproject.apihelper.ServerResponse;
import com.example.user.bpomproject.adapter.ListDashboardBerita;
import com.example.user.bpomproject.apihelper.ApiClient;
import com.example.user.bpomproject.apihelper.ApiService;
import com.example.user.bpomproject.apihelper.Berita;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaduanDashboardFragment extends Fragment {

    ImageView imageView,userAkun;
    TextView textView,lihatsemuaBerita;
    BarChart barChart;
    String token;
    SharedPreferences sharedPreferences;
    Context context;
    ApiService apiService;
    @BindView(R.id.dashboard_berita_rv)
    RecyclerView rvdash;
    ListDashboardBerita adapter;
    List<Berita> beritas;
    List<BarEntry> barEntries;
    TextView notif_guru;

    private int[] mImages = new int[]{
                R.drawable.bpom1,R.drawable.bpom2,R.drawable.bpom3,R.drawable.bpom4
    };

    private String[] iImagesTitle = new String[]{
            "bpom1","bpom2","bpom3","bpom4"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pengaduan_dashboard, container, false);


        ButterKnife.bind(this,v);
        context = v.getContext();
        apiService = ApiClient.getApiService();




        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        token = sharedPreferences.getString("token", "Nilai default");

//        mi =this;
        beritas = new ArrayList<Berita>();
        adapter = new ListDashboardBerita(beritas,context);
        notif_guru = v.findViewById(R.id.notif_guru);
//
//        lihatsemuaBerita = v.findViewById(R.id.lihatsemuaBerita);
//        lihatsemuaBerita.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(PengaduanDashboardFragment.this,PengaduanBeritaFragment.class);
////                startActivity(intent);
//            }
//        });

//        userAkun = v.findViewById(R.id.userAkun);
//        userAkun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,UserAkunActivity.class);
//                startActivity(intent);
//            }
//        });


        CarouselView carouselView = v.findViewById(R.id.carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), iImagesTitle[position], Toast.LENGTH_SHORT).show();
            }
        });

        barChart = v.findViewById(R.id.chart);
        grafik();

        listDash();
        hitung();



        return v;
    }

    private void grafik()
    {
        Call<ServerResponse> call = apiService.dashboard("Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                if (response.body()!=null)
                {
                    {
                        barEntries = new ArrayList<>();
                        for (int i=0; i<resp.getDetail().size(); i++)
                        {
                                barEntries.add(new BarEntry(resp.getDetail().get(i).getBulan(),
                                                            resp.getDetail().get(i).getJumlah()));
                        }

                        BarDataSet barDataSet = new BarDataSet(barEntries,"Grafik PerBulan");
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//                        BarDataSet barDataSet2 = new BarDataSet(barEntries,"Grafik PerBulan");
//                        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData barData = new BarData(barDataSet);
//                        float groupSpace = 0.1f;
//                        float barSpace = 0.02f;
//                        float barWidth = 0.43f;
//                        barData.setBarWidth(barWidth);
//                        barChart.groupBars(1,groupSpace,barSpace);
//                        barChart.setData(barData);

                        barChart.setVisibility(View.VISIBLE);
                        barChart.animateY(1000);
                        barChart.setData(barData);
                        barChart.setDrawValueAboveBar(true);
                        barChart.setFitBars(true);

                        Description description = new Description();
                        description.setText("Grafik");
                        barChart.setDescription(description);
                        barChart.invalidate();


                        String[] months = new String[] {
                                "Jan","Feb","Mar","Apr","Mei","Jun","Jul","Agust","Sep","Okt"
                                ,"Nov","Des"
                        };

//                        XAxis xAxis = barChart.getXAxis();
//                        xAxis.setValueFormatter(new myAxisValueFormatter(months));
//                        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
//                        xAxis.setGranularity(1);
//                        xAxis.setCenterAxisLabels(true);
//                        xAxis.setAxisMinimum(1);
                    }


                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }


//    public class myAxisValueFormatter implements IAxisValueFormatter
//    {
//        private String[] mValues;
//        public myAxisValueFormatter(String[] values)
//        {
//            this.mValues = values;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            return mValues[(int)value];
//        }
//    }

    private void listDash()
    {
        Call<ServerResponse> call = apiService.list_dash_berita("Bearer " + token ,"application/json");
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Berita berita;

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

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                rvdash.setAdapter(adapter);
                rvdash.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    public void hitung()
    {
        Call<ServerResponse> call = apiService.notif("Bearer " + token);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                if (resp.getJumlah() != 0){
                    notif_guru.setText(String.valueOf(resp.getJumlah()));
                }else {
                    notif_guru.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }




}
