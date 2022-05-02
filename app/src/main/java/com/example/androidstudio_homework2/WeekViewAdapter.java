package com.example.androidstudio_homework2;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class WeekViewAdapter extends FragmentStateAdapter {
    //스와이프 동작에 따른 어댑터
    private static int NUM_ITEMS=100;
    //스와이프 횟수는 넉넉하게 100번 잡음
    private int year;
    private int month;
    private int week;

    public WeekViewAdapter(Fragment fa) {
        super(fa);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
    } //year와 month를 현재 날짜로 초기화

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        week = position;
        week = week-(NUM_ITEMS/2);
        int s_p = month+week;
        int w_year;
        int w_month;
        //스와이프된 년과 월
        if((s_p%12)<0) {
            w_year = year+(s_p/12)-1;
            w_month = 12+(s_p%12);
        }
        else {
            w_year = year+(s_p/12);
            w_month = s_p%12;
        }

        return WeekCalendarFragment.newInstance(w_year, w_month);
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
