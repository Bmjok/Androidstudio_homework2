package com.example.androidstudio_homework2;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarFragment extends Fragment {
    //MonthCalendarFragment <-> MonthCalendarAdapter
    ArrayList<String> days = new ArrayList<String>();
    Calendar cal;

    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    private int year;
    private int month;

    public MonthCalendarFragment() {

    }

    public static MonthCalendarFragment newInstance(int year, int month) {
        //매개변수로 년도와 월을 받음
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Calendar.getInstance().set(year, month, 1);
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //액션바 타이틀 변경(setTitle()메소드): https://onlyfor-me-blog.tistory.com/196
        ((MainActivity) getActivity()).setTitle(year+"년 "+month+"월");
        //******** 이대로 두면 안되고, 이동하면 날짜가 바뀌게 수정해야함
        //날짜가 현재는 0년 0월로 뜸

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_monthcalendar,
                container, false);
        GridView gridView = rootView.findViewById(R.id.gridview);
        gridView.setAdapter(
                new ArrayAdapter<String>(
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