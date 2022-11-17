package com.example.moneymanager.Model.Profile;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Model.History.TC_RecycleViewAdapter;
import com.example.moneymanager.R;

import java.util.List;

public class StatusAdaptor extends RecyclerView.Adapter<StatusAdaptor.MyViewHolder> {

    Context context;
    List<StatusCard> statusCardList;

    public StatusAdaptor(Context context, List<StatusCard> statusCardList) {
        this.context = context;
        this.statusCardList = statusCardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.status_layout, parent, false);
        return new StatusAdaptor.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.statusTitle.setText(statusCardList.get(position).getStatusTitle());
        holder.statusLayout.setCardBackgroundColor(Color.parseColor(statusCardList.get(position).getStatusColor()));
        holder.statusIcon.setImageResource(statusCardList.get(position).getStatusIcon());
    }

    @Override
    public int getItemCount() {
        return statusCardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView statusIcon;
        TextView statusTitle;
        CardView statusLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            statusIcon = itemView.findViewById(R.id.statusIcon);
            statusTitle = itemView.findViewById(R.id.statusTitle);
            statusLayout = itemView.findViewById(R.id.statusLayout);
        }
    }
}
