package com.example.user.bpomproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.bpomproject.DetailAduanActivity;
import com.example.user.bpomproject.R;
import com.example.user.bpomproject.apihelper.Pengaduan;

import java.util.List;

public class ListAduanAdapter extends RecyclerView.Adapter<ListAduanAdapter.ViewHolder> {

    List<Pengaduan> mDataset;
    Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View v;

        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListAduanAdapter(List<Pengaduan> myDataset) {
        mDataset = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_aduan_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ListAduanAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

//        ImageView imageView = holder.v.findViewById(R.id.imageBerita);
//
//        Glide
//                .with(context)
//                .load("http://192.168.137.1:8000/api/auth/list_berita"+mDataset.get(position).getGambar())
//                .into(imageView);

        final  int id = mDataset.get(position).getId();
        final  String status =mDataset.get(position).getStatus();
        final  int id_user = mDataset.get(position).getId_user();
        final  String jenis_produk = mDataset.get(position).getJenis_produk();
        final String nama_produk = mDataset.get(position).getNama_produk();
        final String no_reg = mDataset.get(position).getNo_reg();
        final String tgl_exp = mDataset.get(position).getTgl_exp();
        final String nama_pabrik = mDataset.get(position).getNama_pabrik();
        final String alamat_pabrik = mDataset.get(position).getAlamat_pabrik();
        final String no_batch = mDataset.get(position).getNo_batch();
        final String lat = mDataset.get(position).getLat();
        final String lng = mDataset.get(position).getLng();
        final String alamat_beli = mDataset.get(position).getAlamat_beli();
        final String tgl_guna = mDataset.get(position).getTgl_guna();
        final String info_lain = mDataset.get(position).getInfo_lain();
        final String isi = mDataset.get(position).getIsi();
        final String created_at = mDataset.get(position).getCreated_at();
        final String file = mDataset.get(position).getFile();
        final String nama = mDataset.get(position).getNama();
        final String jawaban = mDataset.get(position).getJawaban();
        final int value = mDataset.get(position).getValue();
        final int notif = mDataset.get(position).getNotif();



        TextView textView = holder.v.findViewById(R.id.tvlistIsi);
        TextView textView1 = holder.v.findViewById(R.id.tvdetailaduan);

        if (value != 0)
        {
            textView1.setText(status);
            textView1.setTextColor(Color.WHITE);
            textView1.setBackgroundResource(R.drawable.style_input_button);
        }else {
            textView1.setText(status);
        }

        textView.setText(nama_produk);



        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.v.getContext(),DetailAduanActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("status", status);
                intent.putExtra("id_user",id_user);
                intent.putExtra("jenis_produk",jenis_produk);
                intent.putExtra("nama_produk",nama_produk);
                intent.putExtra("no_reg",no_reg);
                intent.putExtra("tgl_exp",tgl_exp);
                intent.putExtra("nama_pabrik",nama_pabrik);
                intent.putExtra("alamat_pabrik",alamat_pabrik);
                intent.putExtra("no_batch",no_batch);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("alamat_beli",alamat_beli);
                intent.putExtra("tgl_guna",tgl_guna);
                intent.putExtra("info_lain",info_lain);
                intent.putExtra("created_at",created_at);
                intent.putExtra("isi",isi);
                intent.putExtra("file",file);
                intent.putExtra("nama",nama);
                intent.putExtra("jawaban",jawaban);
                intent.putExtra("value",value);
                intent.putExtra("notif",notif);
                v.getContext().startActivity(intent);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
