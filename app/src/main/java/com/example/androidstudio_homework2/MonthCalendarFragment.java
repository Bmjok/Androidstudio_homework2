package com.example.androidstudio_homework2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {
    //MonthCalendarFragment <-> MonthCalendarAdapter
    ArrayList<String> days = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    int year;
    int month;
    static int day = 1;

    public MonthCalendarFragment() {

    }

    public static MonthCalendarFragment newInstance(int year, int month) {
        //매개변수로 년도와 월을 받음
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

        /*
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
        }
        */

        //액션바 타이틀 변경(setTitle()메소드): https://onlyfor-me-blog.tistory.com/196
        ActionBar ab = ((MainActivity) getActivity()).getSupportActionBar();
        ab.setTitle(year+"년 "+(month+1)+"월");
        //******** 이대로 두면 안되고, 이동하면 날짜가 바뀌게 수정해야함

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        View rootView = inflater.inflate(R.layout.fragment_monthcalendar,
                container, false);
        GridView gridView = rootView.findViewById(R.id.gridview);

        int startday = cal.get(Calendar.DAY_OF_WEEK);
        if(startday != 1) {
            for(int i=0; i<startday-1; i++) {
                days.add("");
            }
        } // 매월 1일이 요일과 일치하지 않으면 공백 출력
        for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            days.add("" + (i + 1));
        } // 매월 1일이 요일과 일치하면 (ex: 22년 4월 1일은 금요일) 그때부터 해당 말일(4월은 30일)까지 날짜 출력

        gridView.setAdapter(
                new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        days)); //데이터 추가 필요함 ******************

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Activity activity = getActivity();

                if (activity instanceof OnTitleSelectedListener)
                    ((OnTitleSelectedListener)activity).onTitleSelected((month+1));
                Toast.makeText(getActivity(), month,Toast.LENGTH_SHORT).show();
                //일 부분 수정해야함 **********************
            }
        });

        return rootView;
    }

    //인터페이스 구현
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

}