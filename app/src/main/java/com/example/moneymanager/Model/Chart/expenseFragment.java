package com.example.moneymanager.Model.Chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;
import com.example.moneymanager.database.userDb;

import java.util.ArrayList;
import java.util.List;

public class expenseFragment extends Fragment {

    AnyChartView pie_chart_expense;
    View view;
    RecyclerView expenseRecyclerView;
    LinearLayout summaryLayout;
    TextView emptyDataExpense;
    ImageView emptyImageExpense;
    double totalEntertainmentIncome = 0;
    double totalSocialIncome  = 0;
    double totalBeautyIncome  = 0;
    double totalOtherIncome  = 0;
    double totalEntertainmentExpense = 0;
    double totalSocialExpense = 0;
    double totalBeautyExpense = 0;
    double totalOtherExpense = 0;
    double totalEntertainmentIncomePercent = 0;
    double totalSocialIncomePercent = 0;
    double totalBeautyIncomePercent = 0;
    double totalOtherIncomePercent = 0;
    double totalEntertainmentExpensePercent = 0;
    double totalSocialExpensePercent = 0;
    double totalBeautyExpensePercent = 0;
    double totalOtherExpensePercent = 0;
    String date_month;
    String curUser = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_expense, container, false);
        pie_chart_expense  = view.findViewById(R.id.pie_chart_expense);
        expenseRecyclerView = view.findViewById(R.id.expenseRecyclerView);
        summaryLayout = view.findViewById(R.id.summaryLayout);
        emptyDataExpense = view.findViewById(R.id.emptyDataExpense);
        emptyImageExpense = view.findViewById(R.id.emptyImageExpense);

        //Get the current user
        SharedPreferences sp = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Store the summary card
        chart chartActivity = (chart)getActivity();
        if(!chartActivity.date().equals("")) {
            date_month = chartActivity.date();
        }
        Toast.makeText(chartActivity, date_month, Toast.LENGTH_SHORT).show();

        calculateTotalAmount();
        calculatePercent();

        //Store the summary card
        List<CategorySummaryCard> categorySummaryCardList = new ArrayList<>();
        //Check the existence of each category
        if(totalEntertainmentExpense != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Entertainment",
                    String.valueOf(totalEntertainmentIncomePercent),
                    String.valueOf(totalEntertainmentExpensePercent),
                    String.valueOf(totalEntertainmentIncome),
                    String.valueOf(totalEntertainmentExpense),
                    "Expense"));
        }

        if(totalSocialExpense != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Social & Lifestyle",
                    String.valueOf(totalSocialIncomePercent),
                    String.valueOf(totalSocialExpensePercent),
                    String.valueOf(totalSocialIncome),
                    String.valueOf(totalSocialExpense),
                    "Expense"));
        }

        if (totalBeautyExpense != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Beauty & Health",
                    String.valueOf(totalBeautyIncomePercent),
                    String.valueOf(totalBeautyExpensePercent),
                    String.valueOf(totalBeautyIncome),
                    String.valueOf(totalBeautyExpense),
                    "Expense"));
        }

        if(totalOtherExpense != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Others",
                    String.valueOf(totalOtherIncomePercent),
                    String.valueOf(totalOtherExpensePercent),
                    String.valueOf(totalOtherIncome),
                    String.valueOf(totalOtherExpense),
                    "Expense"));
        }

        CategorySummaryExpenseAdapter categorySummaryAdapter2= new CategorySummaryExpenseAdapter(getActivity(), categorySummaryCardList);
        expenseRecyclerView.setAdapter(categorySummaryAdapter2);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void calculateTotalAmount() {
        try{
            userDb db = userDb.getInstance(getActivity());
            List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);

            String[] tempSelectedDate = date_month.split(" "); //4 Oct 2022 Monthly
            //Check transaction empty or not
            int count = 0;
            for(int i = 0; i < transactionList.size(); i++) {
                String[] tempDate = transactionList.get(i).getDate().split(" "); //4 Oct 2022
                if(tempSelectedDate[3].equals("Daily")) {
                    if(tempDate[0].equals(tempSelectedDate[0])) {
                        if (transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Expense")) {
                            totalEntertainmentExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Expense")) {
                            totalSocialExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Expense")) {
                            totalBeautyExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if(transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Expense")) {
                            totalOtherExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }
                    }
                }else if(tempSelectedDate[3].equals("Monthly")) {
                    if(tempDate[1].equals(tempSelectedDate[1])) {
                        if (transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Expense")) {
                            totalEntertainmentExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Expense")) {
                            totalSocialExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Expense")) {
                            totalBeautyExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if(transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Expense")) {
                            totalOtherExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }
                    }
                } else if (tempSelectedDate[3].equals("Annually")) {
                    if(tempDate[2].equals(tempSelectedDate[2])) {
                        if (transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Expense")) {
                            totalEntertainmentExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Expense")) {
                            totalSocialExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Expense")) {
                            totalBeautyExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }else if(transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Expense")) {
                            totalOtherExpense += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }
                    }
                }
            }

            //If transaction empty then set empty background
            if(count == 0) {
                emptyDataExpense.setVisibility(View.VISIBLE);
                emptyImageExpense.setVisibility(View.VISIBLE);
                pie_chart_expense.setEnabled(false);
                expenseRecyclerView.setVisibility(View.INVISIBLE);
            } else {
                emptyDataExpense.setVisibility(View.INVISIBLE);
                emptyImageExpense.setVisibility(View.INVISIBLE);
                setUpChart();
            }
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        } catch (IndexOutOfBoundsException e1) {
            e1.fillInStackTrace();
        }
    }

    private void calculatePercent() {
        double totalExpense = totalEntertainmentExpense + totalSocialExpense + totalBeautyExpense + totalOtherExpense;

        totalEntertainmentExpensePercent = (int)((totalEntertainmentExpense / (double)totalExpense) * 100);
        totalSocialExpensePercent = (int)((totalSocialExpense / (double)totalExpense) * 100);
        totalBeautyExpensePercent = (int)((totalBeautyExpense / (double)totalExpense) * 100);
        totalOtherExpensePercent = (int)((totalOtherExpense / (double)totalExpense) * 100);
    }

    private void setUpChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntryList = new ArrayList<>();

        String[] category = {"Entertainment", "Social & Lifestyle", "Beauty & Health", "Others"};
        double[] income = {totalEntertainmentExpense, totalSocialExpense, totalBeautyExpense, totalOtherExpense};

        for(int i = 0; i < category.length; i++) {
            dataEntryList.add(new ValueDataEntry(category[i], income[i]));
        }
        pie.data(dataEntryList);
        pie.animation(true, 200);
        pie.labels().position("outside");
        pie_chart_expense.invalidate();
        pie_chart_expense.setChart(pie);
    }
}