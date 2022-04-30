package com.example.androidstudio_homework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthViewAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=100; //12개월

    public MonthViewAdapter(MonthViewFragment fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) { // ***** 수정필요

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = position;

        return MonthCalendarFragment.newInstance(year, month);
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
