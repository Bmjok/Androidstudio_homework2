package com.example.androidstudio_homework2;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {
    //MonthCalendarFragment <-> MonthCalendarAdapter
    ArrayList<String> days = new ArrayList<>();
    private DBHelper mDbHelper;

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    int year;
    int month;
    Calendar cal;
    int date2;
    int hour;

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
        mDbHelper= new DBHelper(getActivity());
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
                //누르면->날짜 토스트메시지 출력
                String date = MonthCal.getItem(position);
                Toast.makeText(getActivity(), (month+1)+"월 "+date+"일",Toast.LENGTH_SHORT).show();
                date2 = position - cal.get(Calendar.DAY_OF_WEEK)+2;
                hour = cal.get(Calendar.HOUR); //월간 달력은 현재 시간 받아오기
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                Cursor cursor = mDbHelper.getUserByDayOfSQL(year,(month+1),date2);
                //(DetailActivity)show_day = year + "년" + month + "월" + day + "일";

                ArrayList<String> schedule = new ArrayList<>(); //스케줄 저장 배열
                ListView lv = (ListView)inflater.inflate(R.layout.dialog, null).findViewById(R.id.dialogView);
                //스케줄을 나열할 리스트뷰 생성
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, schedule);
                //ArrayAdapter 객체 생성, 리스트뷰에 나타낼 객체 '스케줄'을 넣는다.

                //출처: https://hashcode.co.kr/questions/519/
                //커서를 계속 반복해서 스케줄 객체 배열에 제목을 저장한다.
                while(cursor.moveToNext()){ //계속 다음으로 이동, 마지막이면 false
                    String sched = cursor.getString(2); //컬럼 값을 문자열로 구해서 sched에 저장. 2번 컬럼은 title
                    schedule.add(sched); //스케줄 객체 배열에 저장한다. (제목을 저장)
                } //마지막까지 검사하고 다음으로
                if(cursor.moveToFirst() == true){ //데이터가 있을 때
                    lv.setAdapter(adapter); //리스트뷰와 어댑터 연결
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { //리스트뷰 선택시
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String title = schedule.get(position);
                            Intent intent = new Intent(getActivity(), DetailActivity.class);
                            intent.putExtra("title", title);
                            //제목 전달
                            startActivity(intent);
                        }
                    });
                    //스케줄이 저장되어 있을 때만 다이얼로그 출력
                    dialog.setTitle(year + "년" + (month+1) + "월" + date2 + "일");
                    //출처: https://blog.naver.com/zion830/221274053542
                    if (lv.getParent() != null)
                        ((ViewGroup) lv.getParent()).removeView(lv);
                    dialog.setView(lv);
                    dialog.setNegativeButton("뒤로가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //뒤로가기
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
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
                intent.putExtra("day", date2); //선택된 날짜
                intent.putExtra("hour", hour); //현재시간

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