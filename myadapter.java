package com.indianbhaskartools.whatsappstatusdownloader.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.indianbhaskartools.whatsappstatusdownloader.R;
import com.indianbhaskartools.whatsappstatusdownloader.models.AppModel;

import java.util.List;

/**
 * Created by KP on 2/26/2018.
 */

public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {
    Context context;


    private List<AppModel> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.text);
            imageView = (ImageView) v.findViewById(R.id.icon);


        }
    }

    public void add(int position, AppModel item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(AppModel item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public myadapter(List<AppModel> myDataset, Context mCtx) {
        mDataset = myDataset;
        this.context = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_card, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.text.setText(mDataset.get(position).getApp_name());
        Glide.with(holder.imageView.getContext()).load(mDataset.get(position).getImage_url()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = mDataset.get(position).getPackage_name();
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
