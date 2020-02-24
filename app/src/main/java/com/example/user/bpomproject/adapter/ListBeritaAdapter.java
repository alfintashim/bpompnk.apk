package com.example.user.bpomproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.bpomproject.DetailBeritaActivity;
import com.example.user.bpomproject.R;
import com.example.user.bpomproject.apihelper.Berita;

import java.util.List;

public class ListBeritaAdapter extends RecyclerView.Adapter<ListBeritaAdapter.ViewHolder> {

    List<Berita> mDataset;
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
    public ListBeritaAdapter(List<Berita> myDataset,Context context) {
        mDataset = myDataset;
        this.context = context;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent,
                                          int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_berita_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ImageView imageView = holder.v.findViewById(R.id.image);

        Glide
                .with(context)
                .load("http://bpomptk.web.id/uploads/Berita/"+mDataset.get(position).getGambar())
                .into(imageView);

        final  int id = mDataset.get(position).getId();
        final  String judul =mDataset.get(position).getJudul();
        final  String isi = mDataset.get(position).getIsi();
        final  int id_create = mDataset.get(position).getId_create();
        final String gambar = mDataset.get(position).getGambar();
        final String status = mDataset.get(position).getStatus();
        final String name = mDataset.get(position).getName();
        final String created_at = mDataset.get(position).getCreated_at();

        TextView textView = holder.v.findViewById(R.id.judul);
        TextView textView2 = holder.v.findViewById(R.id.tanggal);
        textView.setText(judul);
        textView2.setText(created_at);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.v.getContext(),DetailBeritaActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("judul", judul);
                intent.putExtra("isi",isi);
                intent.putExtra("id_create",id_create);
                intent.putExtra("gambar",gambar);
                intent.putExtra("status",status);
                intent.putExtra("name",name);
                intent.putExtra("created_at",created_at);
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
