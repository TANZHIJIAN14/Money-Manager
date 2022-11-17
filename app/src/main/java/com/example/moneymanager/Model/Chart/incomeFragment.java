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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class incomeFragment extends Fragment {

    AnyChartView pie_chart_income;
    View view;
    RecyclerView incomeRecyclerView;
    LinearLayout summaryLayout;
    TextView emptyData;
    ImageView emptyImage;
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
    int count = 0;
    String date_month;
    String curUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_income, container, false);
        pie_chart_income = view.findViewById(R.id.pie_chart_income);
        incomeRecyclerView = view.findViewById(R.id.incomeRecyclerView);
        summaryLayout = view.findViewById(R.id.summaryLayout);
        emptyData = view.findViewById(R.id.emptyData);
        emptyImage = view.findViewById(R.id.emptyImage);

        //Get the current user
        SharedPreferences sp = getActivity().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        curUser = sp.getString("curUser", "");

        //Store the summary card
        chart chartActivity = (chart)getActivity();
        if(!chartActivity.date().equals("")) {
            date_month = chartActivity.date(); //18 Oct 2022 Monthly
        }
        Toast.makeText(chartActivity, date_month, Toast.LENGTH_SHORT).show();

        calculateTotalAmount();
        calculatePercent();

        List<CategorySummaryCard> categorySummaryCardList = new ArrayList<>();
        //Check the existence of each category
        if(totalEntertainmentIncome != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Entertainment",
                    String.valueOf(totalEntertainmentIncomePercent),
                    String.valueOf(totalEntertainmentExpensePercent),
                    String.valueOf(totalEntertainmentIncome),
                    String.valueOf(totalEntertainmentExpense),
                    "Income"));
        }

        if(totalSocialIncome != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Social & Lifestyle",
                    String.valueOf(totalSocialIncomePercent),
                    String.valueOf(totalSocialExpensePercent),
                    String.valueOf(totalSocialIncome),
                    String.valueOf(totalSocialExpense),
                    "Income"));

        }

        if (totalBeautyIncome != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Beauty & Health",
                    String.valueOf(totalBeautyIncomePercent),
                    String.valueOf(totalBeautyExpensePercent),
                    String.valueOf(totalBeautyIncome),
                    String.valueOf(totalBeautyExpense),
                    "Income"));

        }

        if(totalOtherIncome != 0) {
            categorySummaryCardList.add(new CategorySummaryCard("Others",
                    String.valueOf(totalOtherIncomePercent),
                    String.valueOf(totalOtherExpensePercent),
                    String.valueOf(totalOtherIncome),
                    String.valueOf(totalOtherExpense),
                    "Income"));

        }

        CategorySummaryIncomeAdapter categorySummaryAdapter1 = new CategorySummaryIncomeAdapter(getActivity(), categorySummaryCardList);
        incomeRecyclerView.setAdapter(categorySummaryAdapter1);
        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void calculateTotalAmount() {
        try{
            userDb db = userDb.getInstance(getActivity());
            List<transaction> transactionList = db.userDao().getUserWithTransaction(curUser);
            String[] tempSelectedDate = date_month.split(" "); //4 Oct 2022 Monthly
            count = 0;
            for(int i = 0; i < transactionList.size(); i++) {
                String[] tempDate = transactionList.get(i).getDate().split(" "); //18 Oct 2022
                if(tempSelectedDate[3].equals("Daily")) {
                    if(tempDate[0].equals(tempSelectedDate[0])) {
                        if(transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Income")) {
                            totalEntertainmentIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Income")) {
                            totalSocialIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Income")) {
                            totalBeautyIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Income")) {
                            totalOtherIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }
                    }
                }else if(tempSelectedDate[3].equals("Monthly")) {
                    if(tempDate[1].equals(tempSelectedDate[1])) {
                        if(transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Income")) {
                            totalEntertainmentIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Income")) {
                            totalSocialIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Income")) {
                            totalBeautyIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Income")) {
                            totalOtherIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }
                    }
                } else if (tempSelectedDate[3].equals("Annually")) {
                    if(tempDate[2].equals(tempSelectedDate[2])) {
                        if(transactionList.get(i).getCategory().equals("Entertainment") && transactionList.get(i).getType().equals("Income")) {
                            totalEntertainmentIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Social & Lifestyle") && transactionList.get(i).getType().equals("Income")) {
                            totalSocialIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Beauty & Health") && transactionList.get(i).getType().equals("Income")) {
                            totalBeautyIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        } else if (transactionList.get(i).getCategory().equals("Others") && transactionList.get(i).getType().equals("Income")) {
                            totalOtherIncome += Double.parseDouble(transactionList.get(i).getAmount());
                            count++;
                        }
                    }
                }
            }
            //If transaction empty then set empty background
            if(count == 0) {
                emptyData.setVisibility(View.VISIBLE);
                emptyImage.setVisibility(View.VISIBLE);
                pie_chart_income.setVisibility(View.INVISIBLE);
                summaryLayout.setVisibility(View.INVISIBLE);
            } else {
                emptyData.setVisibility(View.INVISIBLE);
                emptyImage.setVisibility(View.INVISIBLE);
                pie_chart_income.setVisibility(View.VISIBLE);
                summaryLayout.setVisibility(View.VISIBLE);
                setUpChart();
            }
        } catch (NullPointerException e) {
            e.fillInStackTrace();
        } catch (IndexOutOfBoundsException index) {
            index.fillInStackTrace();
        }
    }

    private void calculatePercent() {
        double totalIncome = totalEntertainmentIncome + totalSocialIncome + totalBeautyIncome + totalOtherIncome;

        totalEntertainmentIncomePercent = (int)((totalEntertainmentIncome / (double)totalIncome) * 100);
        totalSocialIncomePercent = (int)((totalSocialIncome / (double)totalIncome) * 100);
        totalBeautyIncomePercent = (int)((totalBeautyIncome / (double)totalIncome) * 100);
        totalOtherIncomePercent = (int)((totalOtherIncome / (double)totalIncome) * 100);
    }

    private void setUpChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntryList = new ArrayList<>();

        String[] category = {"Entertainment", "Social & Lifestyle", "Beauty & Health", "Others"};
        int[] income = {(int)totalEntertainmentIncome, (int)totalSocialIncome, (int)totalBeautyIncome, (int)totalOtherIncome};

        for(int i = 0; i < category.length; i++) {
            dataEntryList.add(new ValueDataEntry(category[i], income[i]));
        }
        pie.data(dataEntryList);
        pie.animation(true, 10);
        pie.labels().position("outside");
        pie_chart_income.invalidate();
        pie_chart_income.setChart(pie);
    }

}




















