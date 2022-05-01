package com.example.androidstudio_homework2;

import static java.util.Calendar.MONTH;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Surface;
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
    ArrayList <String> days = new ArrayList();
    Calendar cal;

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    static MonthCalendarAdapter MonthCal;
    int year;
    int month;
    int start_day;
    int finish_day;

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

    // 인터페이스 추가 정의
    public interface OnTitleSelectedListener {
        public void onTitleSelected(int y, int m, int d);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
            //액션바 타이틀 변경(setTitle()메소드): https://onlyfor-me-blog.tistory.com/196
            ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
            ab.setTitle(year+"년"+(month+1)+"월");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_monthcalendar, container, false);
        // id가 girdview인 그리드뷰 객체를 얻어옴
        GridView gridView = rootView.findViewById(R.id.gridview);

        ArrayList<String> days = new ArrayList<>();

        if(getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90||getActivity().getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270){
            MonthCal = new MonthCalendarAdapter(getActivity(), R.layout.day, days,130);
        }else{  //세로모드일 때
            MonthCal = new MonthCalendarAdapter(getActivity(), R.layout.day, days,250);
        }
        gridView.setAdapter(MonthCal);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String date = MonthCal.getItem(position);
                Toast.makeText(getActivity(), (month+1)+"월 "+date+"일",Toast.LENGTH_SHORT).show();
                //수정해야함 **********************
            }
        });

        return rootView;
    }

    public ArrayList<String> dayOfArray() {
        cal.set(Calendar.DATE, 1);
        start_day = cal.get(Calendar.DAY_OF_WEEK) - 1; //달의 첫 날
        finish_day = cal.getActualMaximum(Calendar.DATE); //달의 마지막 날

        //https://aries574.tistory.com/300
        for (int i=0; i<days.size(); i++) {
            if ( i < start_day || i > (finish_day + start_day - 1) ) days.add("");
            else days.add(Integer.toString(i - start_day + 1));
        }
        return days;
    }

}