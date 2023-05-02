package com.example.sustainwebable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WebsiteCarbonAdapter extends RecyclerView.Adapter<WebsiteCarbonAdapter.ViewHolder> {
    private List<MainActivity.WebsiteCarbonResponse> websiteCarbonResponses;
    private Context context;

    public WebsiteCarbonAdapter(List<MainActivity.WebsiteCarbonResponse> websiteCarbonResponses, Context context) {
        this.websiteCarbonResponses = websiteCarbonResponses;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_website_carbon, parent, false);
        RecyclerView.LayoutParams layp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layp);
        ViewHolder rcv = new ViewHolder((view));

        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainActivity.WebsiteCarbonResponse websiteCarbonResponse = websiteCarbonResponses.get(position);

        holder.urlTextView.setText(websiteCarbonResponse.getUrl());
        holder.bytesTextView.setText(String.valueOf(websiteCarbonResponse.getBytes()));
        holder.gramsTextView.setText(String.format("%.2f", websiteCarbonResponse.getStatistics().getCo2().getGrid().getGrams()));
    }

    @Override
    public int getItemCount() {
        return websiteCarbonResponses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView urlTextView;
        public TextView bytesTextView;
        public TextView gramsTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            bytesTextView = itemView.findViewById(R.id.bytesTextView);
            gramsTextView = itemView.findViewById(R.id.gramsTextView);
        }
    }
}
