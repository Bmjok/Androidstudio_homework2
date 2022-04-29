package com.example.androidstudio_homework2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthGridFragment extends Fragment {
    //교재 DetailsFragment 같은 존재
    //MonthGridFragment <-> MonthGridAdapter
    ArrayList<MonthGridAdapter> items = new ArrayList<MonthGridAdapter>();
    Calendar cal = Calendar.getInstance();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";

    private int year;
    private int month;
    public int day;

    public MonthGridFragment() {

    }

    public static MonthGridFragment newInstance(int year, int month, int day) {
        MonthGridFragment fragment = new MonthGridFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_monthgird,
                container, false);
        GridView gridView = rootView.findViewById(R.id.gridview);
        gridView.setAdapter(
                new ArrayAdapter<MonthGridAdapter>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        items)); //데이터 추가 필요함 ******************

        return rootView;
    }
}