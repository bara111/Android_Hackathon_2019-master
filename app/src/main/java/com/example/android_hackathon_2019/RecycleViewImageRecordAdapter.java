package com.example.android_hackathon_2019;

import android.app.Activity;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecycleViewImageRecordAdapter extends RecyclerView.Adapter<RecycleViewImageRecordAdapter.ViewHolder> implements View.OnClickListener {
    private Activity mContext;

    private ArrayList<ImageRecord> mItems;
    private OnItemClickListener onItemClickListener;

    public RecycleViewImageRecordAdapter(@NonNull Activity context, ArrayList<ImageRecord> items) {
        this.mItems = items;
        this.mContext = context;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);

    }

    public void updateData(ArrayList<ImageRecord> viewModels) {
        mItems.clear();
        mItems.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void addItem(int position, ImageRecord viewModel) {
        mItems.add(position, viewModel);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageRecord item = mItems.get(position);
        holder.type.setText(item.getType());
        if (item.getType().equals("Low Risk")) {
            holder.type.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rec1));

        } else if (item.getType().equals("High Risk")) {
            holder.type.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rec));
        } else if (item.getType().equals("Meduim Risk")) {
            holder.type.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.rec2));
        }
        holder.time.setText(item.getTime());
        Glide.with(mContext)
                .load(item.getImageUrl())
                .centerCrop()
                .into(holder.mImagemage);


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onItemClickListener.onItemClick(v, (ViewModel) v.getTag());
                }
            }, 0);
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImagemage;
        public TextView type, time, title, description;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tvName);
            time = itemView.findViewById(R.id.tvTime);
            mImagemage=itemView.findViewById(R.id.image);


        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, ViewModel viewModel);

    }
}