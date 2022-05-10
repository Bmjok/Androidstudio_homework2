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

    ArrayList<String> days_1 = new ArrayList<>(); //공백, 날짜 저장
    ArrayList<String> days_2 = new ArrayList<>(); //일주일 간격 출력
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
            week = getArguments().getInt(ARG_PARAM3);
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
        //화면에 뿌리는건 days_2번을 뿌려야함!!

        WeekCalendarAdapter WeekCal;
        //[가로세로 로테이션] 출처: https://faith-developer.tistory.com/10
        //가로모드일 때
        if(getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90||getActivity().getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270){
            WeekCal = new WeekCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1,days_3,130);
        }else{ //세로모드일 때
            WeekCal = new WeekCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1,days_3,250);
        }
        W_gridview.setAdapter(W2);
        week_gridview.setAdapter(WeekCal);

        week_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "position = "+days_1.get(position%7),Toast.LENGTH_SHORT).show();
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

        //첫째주: week==0, 둘째주: week==1, 셋째주: week==2, 넷째주: week==3, 다섯째주: week==4
        //가끔 여섯째주 있음...(ex: 22년 7월) week==5

        if(start_day != 1) {
            for(i=0; i<start_day-1; i++) {
                days_1.add(" ");
            }
        } //주간 달력도 1일과 첫 요일을 맞춰야 함. 안맞으면 공백으로 출력하기.
        for (i=1; i<=finish_day; i++) {
            days_1.add(Integer.toString(i));
        } //이 부분을 수정해야함. 출력할 때 일주일씩 보여야 함. -> days_2를 하나 더 만드는 걸로 수정
        for (i=1; i<42-(start_day+finish_day-1)+1; i++) {
            days_1.add(" ");
        } //마지막 부분 공백 출력(월이 바로 바뀌면 안됨)

        if(week==0) { //첫째주 (여기서 i는 날짜가 아니고 인덱스임!)
            for(i=0; i<=6; i++) {
                days_2.add(days_1.get(i)); //1번 배열에 있는 i번째를 2번 배열에 추가해서 출력하는 방식
            }
        }
        else if(week==1) { //둘째주
            for(i=7; i<=13; i++) {
                days_2.add(days_1.get(i));
            }
        }
        else if(week==2) { //셋째주
            for(i=14; i<=20; i++) {
                days_2.add(days_1.get(i));
            }
        }
        else if(week==3) { //넷째주
            for(i=21; i<=27; i++) {
                days_2.add(days_1.get(i));
            }
        }
        else if(week==4) { //다섯째주
            for(i=28; i<=34; i++) {
                days_2.add(days_1.get(i));
            }
        } //배열의 크기보다 커져서 오류 발생
        else if(week==5) { //여섯째주
            for(i=35; i<(start_day+finish_day-1); i++) {
                days_2.add(days_1.get(i));
            }
        }
    }

    private void getBlank() { // *************** 이 함수는 주간 격자 출력 함수
        for(int i=1;i<=168;i++){
            days_3.add("");
        }
    }

}