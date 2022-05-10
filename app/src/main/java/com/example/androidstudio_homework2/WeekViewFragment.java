package com.example.androidstudio_homework2;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekViewFragment extends Fragment {

    ArrayList<String> days = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    int year;
    int month;
    int week;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param2";

    private String day;

    public WeekViewFragment() {
        // Required empty public constructor
    }

    public static WeekViewFragment newInstance(int year, int month, int week) {
        WeekViewFragment fragment = new WeekViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, week);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            week = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        week = cal.get(Calendar.WEEK_OF_MONTH);
        ActionBar ab =((MainActivity)getActivity()).getSupportActionBar();

        View rootView = inflater.inflate(R.layout.fragment_weekview,
                container, false);
        ViewPager2 vpPager = rootView.findViewById(R.id.week_vpPager);
        FragmentStateAdapter adapter = new WeekViewAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(week-1,false);
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int week = position;
                year = cal.get(Calendar.YEAR)+month/12;
                month = cal.get(Calendar.MONTH)+week/5;
                ab.setTitle(year+"년"+(month+1)+"월");
            }
        });
        return rootView;
    }
}