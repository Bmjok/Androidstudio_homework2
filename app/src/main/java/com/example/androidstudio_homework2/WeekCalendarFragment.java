package com.example.androidstudio_homework2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekCalendarFragment extends Fragment {
    // WeekCalendarFragment <-> WeekCalendarAdapter

    ArrayList<String> days_1 = new ArrayList<>();
    ArrayList<String> days_2 = new ArrayList<>();
    ArrayList<String> days_3 = new ArrayList<>(); //공백 격자 저장

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "week";

    int year;
    int month;
    int week;
    public static int day;
    Calendar cal;

    public WeekCalendarFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WeekCalendarFragment newInstance(int param1, int param2, int week) {
        WeekCalendarFragment fragment = new WeekCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, week%6);
        fragment.setArguments(args);
        return fragment;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weekcalendar, container, false);

        cal = Calendar.getInstance();

        GridView W_gridview = (GridView)rootView.findViewById(R.id.gridview);
        GridView week_gridview = (GridView)rootView.findViewById(R.id.week_gridview);

        takeWeekCalendar();
        getBlank();

        WeekCalendarAdapter W2 = new WeekCalendarAdapter(getActivity(), android.R.layout.simple_list_item_1, days_2);
        W_gridview.setAdapter(W2);

        WeekCalendarAdapter WeekCal;

        //가로모드일 때
        if(getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90||getActivity().getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270){
            WeekCal = new WeekCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1, days_3,130);
        }else{  //세로모드일 때
            WeekCal = new WeekCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1, days_3,250);
        }
        week_gridview.setAdapter(WeekCal);

        week_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "position = "+position,Toast.LENGTH_SHORT).show();
                // *************** 토스트 메시지 date 부분 수정 ***************
                // 선택된 격자의 position을 나타내야함. (날짜랑은 다름)
            }
        });

        return rootView;

    }

    private void takeWeekCalendar() { // *************** 이 함수는 요일 밑에 한 줄을 나타냄, 일주일씩만 출력함
        // *************** 주간 출력 함수 다시 만들어야 함 ***************
        cal.set(year, month, 1);
        int start_day = cal.get(Calendar.DAY_OF_WEEK); //첫 날
        int finish_day = cal.getActualMaximum(Calendar.DATE); //마지막 날
        int i;

        if (start_day != 1) {
            for (i = 0; i < start_day - 1; i++) {
                days_1.add(" ");
            }
        }
        for (i=1; i<=finish_day; i++) {
            days_1.add(Integer.toString(i));
        }
    }

    private void getBlank() { // *************** 이 함수는 주간 격자 출력 함수
        for(int i=1;i<=168;i++){
            days_3.add("");}
    }

}