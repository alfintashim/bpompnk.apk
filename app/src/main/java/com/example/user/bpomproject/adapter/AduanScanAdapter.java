package com.example.user.bpomproject.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.bpomproject.InputActivity;
import com.example.user.bpomproject.R;

import java.util.List;

public class AduanScanAdapter extends RecyclerView.Adapter<AduanScanAdapter.ViewHolder> {

    List<Bitmap> mDataset;
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
    public AduanScanAdapter(List<Bitmap> myDataset) {
        mDataset = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_aduan_scan, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AduanScanAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element



        ImageView imageView = holder.v.findViewById(R.id.ivScanImage);
        TextView textView = holder.v.findViewById(R.id.tvScanNumber);

        imageView.setImageBitmap(resizeBitmap(mDataset.get(position),200,200));
        textView.setText("#"+(position+1));

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
                alertDialogBuilder.setMessage("Hapus Gambar?");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InputActivity.ma.hapusScan(position);

                    }
                });
                alertDialogBuilder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();

                return false;
            }
        });


    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private Bitmap resizeBitmap(Bitmap bm , int width, int height){
        return Bitmap.createScaledBitmap(bm,width,height,false);
    }

}
