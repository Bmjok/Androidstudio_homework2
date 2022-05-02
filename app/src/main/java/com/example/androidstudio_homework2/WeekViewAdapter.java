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
        days = cal.get(Calendar.DATE);
    } //year와 month를 현재 날짜로 초기화

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        Calendar cal = Calendar.getInstance();
        position = position-(NUM_ITEMS/2);
        int finish_day = cal.getActualMaximum(Calendar.DATE); //달의 마지막 날
        int w_week = days + (position * 7); //중요!
        int w_year = year;
        int w_month = month;
        //스와이프된 년과 월

        if(w_week > finish_day) { //스와이프 해서 넘어간 페이지의 날짜가 마지막 날짜보다 클 때
            //ex: 마지막 날은 31일, 현재는 26일, +7일 뒤는 33일
            while(w_week > finish_day) { //w_week는 한 번 넘길 때마다 +7씩 추가
                cal.set(Calendar.YEAR, w_year);
                cal.set(Calendar.MONTH, w_month);
                finish_day = cal.getActualMaximum(Calendar.DATE);
                w_week = w_week-finish_day; //작아질 때까지 계산
                if(w_month == 11) { //month의 index는 실제 달보다 1 작다.
                    w_year ++;
                    w_month = 0; //month의 index는 실제 달보다 1 작다.
                } else {
                    w_month ++; //현재 month가 1월부터 11월 사이에 존재하면 month만 증가해주면 된다.
                }
            }
        } else if(w_week < 1) { //fragment를 왼쪽으로 옮길 때

            while ( w_week < 1 ) {

                if ( w_week < 0 ) {
                    w_year--;
                    w_month = 11;
                } else {
                    w_month--;
                }
                cal.set(Calendar.YEAR, w_year);
                cal.set(Calendar.MONTH, w_month);
                int Previous_m = cal.getActualMaximum(Calendar.DATE); //이전 달
                w_week += Previous_m;

            }
        }

        return WeekCalendarFragment.newInstance(w_year, w_month);
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
