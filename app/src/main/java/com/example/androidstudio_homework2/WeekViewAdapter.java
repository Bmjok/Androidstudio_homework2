package com.example.androidstudio_homework2;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class WeekViewAdapter extends FragmentStateAdapter {
    //스와이프 동작에 따른 어댑터
    private static int NUM_ITEMS=1000;
    //스와이프 횟수는 넉넉하게 1000번 잡음(week는 month보다 더 많으니까)
    private int year;
    private int month;
    private int days;
    private int week;

    public WeekViewAdapter(Fragment fa) {
        super(fa);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        week = cal.get(Calendar.WEEK_OF_MONTH);
    } //year와 month를 현재 날짜로 초기화

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        int n_year;
        int n_month;
        n_year = position%52;
        n_month = position%12;
        position = position%6;
        int week = position;
        return WeekCalendarFragment.newInstance(n_year, n_month, week);
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
