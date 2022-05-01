package com.example.androidstudio_homework2;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {
    //MonthCalendarFragment <-> MonthCalendarAdapter
    ArrayList<String> days = new ArrayList<>();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    int year;
    int month;
    public static int day;
    Calendar cal;

    public MonthCalendarFragment() {
        // Required empty public constructor
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

        cal = Calendar.getInstance();

        GridView gridview = (GridView)rootView.findViewById(R.id.gridview);

        takeCalendar();

        MonthCalendarAdapter MonthCal;

        //가로모드일 때
        if(getActivity().getWindowManager().getDefaultDisplay().getRotation()
                == Surface.ROTATION_90||getActivity().getWindowManager().getDefaultDisplay().getRotation()== Surface.ROTATION_270){
            MonthCal = new MonthCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1, days,130);
        }else{  //세로모드일 때
            MonthCal = new MonthCalendarAdapter(getActivity(),android.R.layout.simple_list_item_1, days,250);
        }
        gridview.setAdapter(MonthCal);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String date = MonthCal.getItem(position);
//                Toast.makeText(getActivity(), (month+1)+"월 "+date+"일",Toast.LENGTH_SHORT).show();
//            }
//        });

        return rootView;
    }

    private void takeCalendar(){
        
    }
}