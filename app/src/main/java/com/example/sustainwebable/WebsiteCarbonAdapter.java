package com.example.sustainwebable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WebsiteCarbonAdapter extends RecyclerView.Adapter<WebsiteCarbonAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<MainActivity.WebsiteCarbonResponse> websiteCarbonResponses;
    private Context context;

    public WebsiteCarbonAdapter(List<MainActivity.WebsiteCarbonResponse> websiteCarbonResponses, Context context, RecyclerViewInterface recyclerViewInterface) {
        this.websiteCarbonResponses = websiteCarbonResponses;
        this.context=context;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_website_carbon, parent, false);
        RecyclerView.LayoutParams layp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layp);
        ViewHolder rcv = new ViewHolder(view,recyclerViewInterface);

        return rcv;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainActivity.WebsiteCarbonResponse websiteCarbonResponse = websiteCarbonResponses.get(position);

        holder.urlTextView.setText(websiteCarbonResponse.getUrl());
        holder.bytesTextView.setText(String.valueOf(websiteCarbonResponse.getBytes()));
        holder.gramsTextView.setText(String.format("%.2f", websiteCarbonResponse.getStatistics().getCo2().getGrid().getGrams()));
        holder.urlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = websiteCarbonResponse.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return websiteCarbonResponses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView urlTextView;
        public TextView bytesTextView;
        public TextView gramsTextView;

        public ViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            bytesTextView = itemView.findViewById(R.id.bytesTextView);
            gramsTextView = itemView.findViewById(R.id.gramsTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface!=null){
                        int pos = getBindingAdapterPosition();

                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
