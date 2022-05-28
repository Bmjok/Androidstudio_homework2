package com.example.androidstudio_homework2;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {
    //MonthCalendarFragment <-> MonthCalendarAdapter
    ArrayList<String> days = new ArrayList<>();

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    int year;
    int month;
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
        cal = Calendar.getInstance();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_monthcalendar, container, false);
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

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String date = MonthCal.getItem(position);
                Toast.makeText(getActivity(), (month+1)+"월 "+date+"일",Toast.LENGTH_SHORT).show();
            }
        });

        //floating button 추가
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month+1);

                startActivity(intent);
            }
        });

        return rootView;
    }

    private void takeCalendar(){
        //https://aries574.tistory.com/300
        //그냥 저번에 했던 거 변형했어요!
        cal.set(year,month,1);
        int start_day = cal.get(Calendar.DAY_OF_WEEK); //첫 날
        int finish_day = cal.getActualMaximum(Calendar.DATE); //마지막 날
        int i;
        //https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=my_stroy_1996&logNo=220775994665
        //달력 출력 계산 참고
        if(start_day != 1) {
            for(i=0; i<start_day-1; i++) {
                days.add(" ");
            }
        } // 매월 1일이 요일과 일치하지 않으면 공백 출력
        for (i=1; i<=finish_day; i++) {
            days.add(Integer.toString(i)); //형변환
        } // 매월 1일이 요일과 일치하면 (ex: 22년 4월 1일은 금요일) 그때부터 해당 말일(4월은 30일)까지 날짜 출력
        //날짜는 1일부터 마지막까지
        //https://croute.me/335 [그리드뷰 달력 그리기 예제]
        for (i=1; i<42-(start_day+finish_day-1)+1; i++) {
            days.add(" "); //달력 나머지 부분 흰색 그리드뷰로 채우기
        }
    }
}