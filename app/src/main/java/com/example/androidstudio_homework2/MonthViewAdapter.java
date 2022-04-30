package com.example.androidstudio_homework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MonthViewAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=3;

    public MonthViewAdapter(MonthViewFragment fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                MonthCalendarFragment MonthCal = new MonthCalendarFragment();
                return MonthCal;
            default:
                return null;
        }
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
