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
    Calendar cal = Calendar.getInstance();
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    private int year;
    private int month;
    private FragmentActivity fragmentActivity;

    public MonthFragment() {

    }

    public static MonthCalendarFragment newInstance(int year, int month) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
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
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        FragmentStateAdapter adapter = new MonthVPAdapter(fragmentActivity);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        return rootView;
    }
}