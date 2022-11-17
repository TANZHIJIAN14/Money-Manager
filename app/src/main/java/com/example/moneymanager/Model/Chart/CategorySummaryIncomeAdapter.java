package com.example.moneymanager.Model.Chart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.Model.History.TC_RecycleViewAdapter;
import com.example.moneymanager.R;

import java.util.HashMap;
import java.util.List;

public class CategorySummaryIncomeAdapter extends RecyclerView.Adapter<CategorySummaryIncomeAdapter.MyViewHolder> {

    Context context;
    List<CategorySummaryCard>categorySummaryCardList;

    public CategorySummaryIncomeAdapter(Context context, List<CategorySummaryCard> categorySummaryCardList) {
        this.context = context;
        this.categorySummaryCardList = categorySummaryCardList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_summary, parent, false);
        return new CategorySummaryIncomeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Store bar types
        HashMap<String, Integer> mapCategoryColor = new HashMap<>();
        mapCategoryColor.put("Entertainment", R.drawable.summarypurplebg);
        mapCategoryColor.put("Social & Lifestyle", R.drawable.summarybluebg);
        mapCategoryColor.put("Beauty & Health", R.drawable.summarygreenbg);
        mapCategoryColor.put("Others", R.drawable.summaryyellowbg);

        if(!categorySummaryCardList.get(position).getCategoryTotalAmountIncome().equals("") && categorySummaryCardList.get(position).getTypeCashFlow().equals("Income")) {
            holder.category_percentage_layout.setBackgroundResource(mapCategoryColor.get(categorySummaryCardList.get(position).getCategory()));
            holder.category_percentage.setText(categorySummaryCardList.get(position).getCategoryPercentageIncome() + "%");
            holder.category_title.setText(categorySummaryCardList.get(position).getCategory());
            holder.categoryTotalAmount.setText("RM" + categorySummaryCardList.get(position).getCategoryTotalAmountIncome());
        }
    }

    @Override
    public int getItemCount() {
        return categorySummaryCardList.size();
    }

    public static class MyViewHolder extends TC_RecycleViewAdapter.MyViewHolder {
        LinearLayout category_percentage_layout;
        TextView category_percentage, category_title, categoryTotalAmount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            category_percentage_layout = itemView.findViewById(R.id.category_percentage_layout);
            category_percentage = itemView.findViewById(R.id.category_percentage);
            category_title = itemView.findViewById(R.id.category_title);
            categoryTotalAmount = itemView.findViewById(R.id.categoryTotalAmount);
        }
    }
}
