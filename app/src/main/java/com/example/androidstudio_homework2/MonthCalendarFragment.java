package com.example.androidstudio_homework2;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {
    //MonthCalendarFragment <-> MonthCalendarAdapter
    ArrayList<MonthCalendarAdapter> days = new ArrayList<MonthCalendarAdapter>();
    Calendar cal = Calendar.getInstance();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";

    private int year;
    private int month;
    public int day;

    public MonthCalendarFragment() {

    }

    public static MonthCalendarFragment newInstance(int year, int month, int day) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
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

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        View rootView = inflater.inflate(R.layout.fragment_monthcalendar,
                container, false);
        GridView gridView = rootView.findViewById(R.id.gridview);
        gridView.setAdapter(
                new ArrayAdapter<MonthCalendarAdapter>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        days)); //데이터 추가 필요함 ******************

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Activity activity = getActivity();

                if (activity instanceof OnTitleSelectedListener)
                    ((OnTitleSelectedListener)activity).onTitleSelected(position);
            }
        });

        return rootView;
    }

    //인터페이스 구현
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

}