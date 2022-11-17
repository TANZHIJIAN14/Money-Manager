package com.example.moneymanager.Model.Chart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;

import java.util.HashMap;
import java.util.List;

public class CategorySummaryExpenseAdapter extends RecyclerView.Adapter<CategorySummaryExpenseAdapter.MyViewHolder> {

    Context context;
    List<CategorySummaryCard> categorySummaryCardList;

    public CategorySummaryExpenseAdapter(Context context, List<CategorySummaryCard> categorySummaryCardList) {
        this.context = context;
        this.categorySummaryCardList = categorySummaryCardList;
    }

    @NonNull
    @Override
    public CategorySummaryExpenseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_summary, parent, false);
        return new CategorySummaryExpenseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySummaryExpenseAdapter.MyViewHolder holder, int position) {
        //Store bar types
        HashMap<String, Integer> mapCategoryColor = new HashMap<>();
        mapCategoryColor.put("Entertainment", R.drawable.summarypurplebg);
        mapCategoryColor.put("Social & Lifestyle", R.drawable.summarybluebg);
        mapCategoryColor.put("Beauty & Health", R.drawable.summarygreenbg);
        mapCategoryColor.put("Others", R.drawable.summaryyellowbg);

        if(!categorySummaryCardList.get(position).getCategoryTotalAmountExpense().equals("") && categorySummaryCardList.get(position).getTypeCashFlow().equals("Expense")) {
            holder.category_percentage_layout.setBackgroundResource(mapCategoryColor.get(categorySummaryCardList.get(position).getCategory()));
            holder.category_percentage.setText(categorySummaryCardList.get(position).getCategoryPercentageExpense() + "%");
            holder.category_title.setText(categorySummaryCardList.get(position).getCategory());
            holder.categoryTotalAmount.setText("RM" + categorySummaryCardList.get(position).getCategoryTotalAmountExpense());
        }
    }

    @Override
    public int getItemCount() {
        return categorySummaryCardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
