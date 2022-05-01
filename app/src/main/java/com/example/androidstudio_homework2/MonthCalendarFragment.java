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
    ArrayList<String> days = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    int year;
    int month;
    static int day = 1;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cal.set(cal.get(Calendar.YEAR), MONTH+1, 1);
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);

            //액션바 타이틀 변경(setTitle()메소드): https://onlyfor-me-blog.tistory.com/196
            ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
            ab.setTitle(year+"년"+(month+1)+"월");

            takeCalendar(year, month);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        View rootView = inflater.inflate(R.layout.fragment_monthcalendar,
                container, false);
        MonthCalendarAdapter MonthCal = new MonthCalendarAdapter
                (getActivity().getApplicationContext(), day, days);
        GridView gridView = rootView.findViewById(R.id.gridview);
        gridView.setAdapter(MonthCal);

        if(getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90||getActivity().getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270){
            //어댑터 준비 (배열 객체 이용, simple_list_item_1 리소스 사용)
            MonthCal = new MonthCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1,days,130);
        }else{  //세로모드일 때
            MonthCal = new MonthCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1,days,250);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), month,Toast.LENGTH_SHORT).show();
                //일 부분 수정해야함 **********************
            }
        });

        return rootView;
    }

    private ArrayList<String> takeCalendar(int year, int month) {
        ArrayList<String> dayList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        start_day = cal.get(Calendar.DAY_OF_WEEK) - 1; //달의 첫 날
        finish_day = cal.getActualMaximum(Calendar.DATE); //달의 마지막 날

        //https://aries574.tistory.com/300
        for (int i=0; i<days.size(); i++) {
            if ( i < start_day || i > (finish_day + start_day - 1) ) days.add("");
            else days.add(Integer.toString(i - start_day + 1));
        }

        return dayList;
    }

}