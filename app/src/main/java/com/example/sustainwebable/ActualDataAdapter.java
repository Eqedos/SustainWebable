package com.example.sustainwebable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActualDataAdapter extends RecyclerView.Adapter<ActualDataAdapter.ViewHolder> {
    ActualDataModel[] actualData;
    Context context;

    public ActualDataAdapter(ActualDataModel[] actualData, MainActivity3 activity) {
        this.actualData = actualData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_actual_values, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ActualDataModel actualDataModelList = actualData[position];
        holder.urlTextView.setText(actualDataModelList.getUrl());
        holder.bytesTextView3.setText(String.valueOf(actualDataModelList.getBytes()));
        holder.gridTextView.setText(String.valueOf(actualDataModelList.getGrid()));
        holder.renewableTextView3.setText(String.valueOf(actualDataModelList.getRenewable()));
        holder.greenTextView3.setText(String.valueOf(actualDataModelList.isGreen()));
        holder.cleanerthanTextView3.setText(String.valueOf(actualDataModelList.getCleanerthan()));
        holder.energyTextView3.setText(String.valueOf(actualDataModelList.getEnergy()));

    }

    @Override
    public int getItemCount() {
        return actualData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView urlTextView;
        TextView bytesTextView3;
        TextView gridTextView;
        TextView renewableTextView3;
        TextView greenTextView3;
        TextView cleanerthanTextView3;
        TextView energyTextView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            bytesTextView3 = itemView.findViewById(R.id.bytesTextView3);
            gridTextView = itemView.findViewById(R.id.gridTextView);
            renewableTextView3 = itemView.findViewById(R.id.renewableTextView3);
            greenTextView3 = itemView.findViewById(R.id.greenTextView3);
            cleanerthanTextView3 = itemView.findViewById(R.id.cleanerthanTextView3);
            energyTextView3 = itemView.findViewById(R.id.energyTextView3);


        }
    }

}
