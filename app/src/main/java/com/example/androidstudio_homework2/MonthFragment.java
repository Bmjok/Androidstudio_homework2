package com.example.androidstudio_homework2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class MonthFragment extends Fragment {
    //MonthFragment <-> MonthGridAdapter
    Calendar cal = Calendar.getInstance();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";

    private int year;
    private int month;
    public int day;

    public MonthFragment() {

    }

    public static MonthFragment newInstance(int year, int month, int day) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        return rootView;
    }
}