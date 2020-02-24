package com.example.user.bpomproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.bpomproject.R;
import com.example.user.bpomproject.apihelper.Chat;

import java.util.List;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {
    List<Chat> mDataset;
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
    public ListChatAdapter(List<Chat> myDataset) {
        mDataset = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_chat_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ListChatAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

//        ImageView imageView = holder.v.findViewById(R.id.imageBerita);
//
//        Glide
//                .with(context)
//                .load("http://192.168.137.1:8000/api/auth/list_berita"+mDataset.get(position).getGambar())
//                .into(imageView);

        final  int id = mDataset.get(position).getId();
        final int id_aduan = mDataset.get(position).getId_aduan();
        final int id_user = mDataset.get(position).getId_user();
        final int id_role = mDataset.get(position).getId_role();
        final String pesan = mDataset.get(position).getPesan();
        final String name = mDataset.get(position).getName();
        final String created_at = mDataset.get(position).getCreated_at();

        final TextView pesanChat = holder.v.findViewById(R.id.pesanChat);
//        final TextView pesanChat2 = holder.v.findViewById(R.id.pesanChat2);
        View pesanBantuan = holder.v.findViewById(R.id.pesanBantuan);
        View pesanBantuan2 = holder.v.findViewById(R.id.pesanBantuan2);
//        View pesanBantuan3 = holder.v.findViewById(R.id.pesanBantuan3);




        if (id_role == 3) {
            pesanChat.setText(pesan);
//            pesanChat2.setText(name);
            pesanChat.setTextColor(Color.BLACK);
            pesanBantuan.setVisibility(View.GONE);
            pesanChat.setBackgroundResource(R.drawable.syle_bitellipse_fillred_chat);
        } else if (id_role == 4){
            pesanChat.setText(pesan);
//            pesanChat2.setText(name);
            pesanBantuan.setVisibility(View.VISIBLE);
            pesanChat.setTextColor(Color.WHITE);
            pesanChat.setBackgroundResource(R.drawable.syle_bitellipse_fillred_chat2);
        }


        if (id_role == 4) {
            pesanBantuan2.setVisibility(View.GONE);
        } else {
            pesanBantuan2.setVisibility(View.GONE);
        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
