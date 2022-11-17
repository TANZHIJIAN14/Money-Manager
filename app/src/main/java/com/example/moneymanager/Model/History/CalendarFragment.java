package com.example.moneymanager.Model.History;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanager.R;
import com.example.moneymanager.database.transaction;
import com.example.moneymanager.database.transactionDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarFragment extends DialogFragment implements View.OnClickListener {

    CalendarView calendarView;
    Button saveDate;
    RadioGroup categoryGroup_filter;
    RadioButton categoryBtn_filter;
    String date = "";

    @Override
    public void onClick(View view) {
        int id = categoryGroup_filter.getCheckedRadioButtonId();
        categoryBtn_filter = view.findViewById(id);
    }

    public interface onInputListener{
        void sendInput(String date, String category);
    }

    public onInputListener onInputListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.transaction_category, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        saveDate = view.findViewById(R.id.saveDate);
        categoryGroup_filter = view.findViewById(R.id.categoryGroup_filter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 + " " + stringMonth((i1 + 1)) + " " + i;
                date = addZero(date);
                Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
            }
        });



        //Get the category chose
        int id = categoryGroup_filter.getCheckedRadioButtonId();
        if(id > -1) {
            categoryBtn_filter = view.findViewById(id);
        } else {
            categoryBtn_filter = null;
        }

        view.findViewById(R.id.entertainment_filter).setOnClickListener(this);
        view.findViewById(R.id.social_lifestyle_filter).setOnClickListener(this);
        view.findViewById(R.id.beauty_health_filter).setOnClickListener(this);
        view.findViewById(R.id.other_filter).setOnClickListener(this);

        saveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String category = "";
                    if(categoryBtn_filter != null) {
                        category = categoryBtn_filter.getText().toString();
                    }

                    if(date.equals("") && !category.equals("")) {
                        ((history)getActivity()).date_category.setText("Date - N/A" + " \nCategory - " + category);
                    } else if (!date.equals("") && category.equals("")) {
                        ((history)getActivity()).date_category.setText("Date - " + date + " \nCategory - N/A");
                    } else {
                        ((history)getActivity()).date_category.setText("Date - " + date + " \nCategory - " + category);
                    }
                    onInputListener.sendInput(date, category);
                    getDialog().dismiss();
                } catch(NullPointerException e) {
                    e.fillInStackTrace();
                }
            }
        });

        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onInputListener = (CalendarFragment.onInputListener) getActivity();
        }catch (ClassCastException e) {
            e.fillInStackTrace();
        }
    }

    private String stringMonth(int i) {
        HashMap<Integer, String> months = new HashMap<>();
        months.put(1, "Jan");
        months.put(2, "Feb");
        months.put(3, "Mar");
        months.put(4, "Apr");
        months.put(5, "May");
        months.put(6, "Jun");
        months.put(7, "Jul");
        months.put(8, "Aug");
        months.put(9, "Sep");
        months.put(10, "Oct");
        months.put(11, "Nov");
        months.put(12, "Dec");
        return months.get(i);
    }

    public String addZero(String date) {
        String result = "";
        if(date.length() < 11) {
            return result = "0" + date;
        }
        return date;
    }
}
