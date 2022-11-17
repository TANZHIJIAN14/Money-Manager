package com.example.moneymanager.Model.History;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Model.Transaction.EditTransaction;
import com.example.moneymanager.Model.Transaction.TransactionCard;
import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;

import java.util.HashMap;
import java.util.List;

public class TC_RecycleViewAdapter extends RecyclerView.Adapter<TC_RecycleViewAdapter.MyViewHolder> {

    Context context;
    List<transaction> transactionCardList;

    public TC_RecycleViewAdapter(Context context, List<transaction> transactionCardList) {
        this.context = context;
        this.transactionCardList = transactionCardList;
    }

    @NonNull
    @Override
    public TC_RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.transactioncard, parent, false);
        return new TC_RecycleViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TC_RecycleViewAdapter.MyViewHolder holder, int position) {
        //Store bar types
        HashMap<String, Integer> mapCategoryBar = new HashMap<>();
        mapCategoryBar.put("Entertainment", R.drawable.purplebar1);
        mapCategoryBar.put("Social & Lifestyle", R.drawable.bluebar1);
        mapCategoryBar.put("Beauty & Health", R.drawable.greenbar1);
        mapCategoryBar.put("Others", R.drawable.pinkbar1);

        //Store car icons
        HashMap<String, Integer> mapCategoryIcon = new HashMap<>();
        mapCategoryIcon.put("Entertainment", R.drawable.entainment);
        mapCategoryIcon.put("Social & Lifestyle", R.drawable.social);
        mapCategoryIcon.put("Beauty & Health", R.drawable.beauty);
        mapCategoryIcon.put("Others", R.drawable.others);

        holder.cardCategoryBar.setImageResource(mapCategoryBar.get(transactionCardList.get(position).getCategory()));
        holder.cardCategoryIcon.setImageResource(mapCategoryIcon.get(transactionCardList.get(position).getCategory()));
        if(transactionCardList.get(position).getType().equals("Income")) {
            holder.cardAmount.setText("+$ " + transactionCardList.get(position).getAmount());
        } else {
            holder.cardAmount.setText("-$ " + transactionCardList.get(position).getAmount());
        }
        holder.cardDes.setText(transactionCardList.get(position).getTitle());
        holder.cardDate.setText(transactionCardList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return transactionCardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cardCategoryBar, cardCategoryIcon;
        TextView cardAmount, cardDes, cardDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardCategoryBar = itemView.findViewById(R.id.cardCategoryBar);
            cardCategoryIcon = itemView.findViewById(R.id.cardCategoryIcon);
            cardAmount = itemView.findViewById(R.id.cardAmount);
            cardDes = itemView.findViewById(R.id.cardDes);
            cardDate = itemView.findViewById(R.id.cardDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), EditTransaction.class);
                    SharedPreferences sp = itemView.getContext().getSharedPreferences("cardTitle",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("title", cardDes.getText().toString());
                    editor.apply();
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
