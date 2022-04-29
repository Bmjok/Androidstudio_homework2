package com.example.androidstudio_homework2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthVPAdapter extends FragmentStateAdapter {

    public MonthVPAdapter(@NonNull MonthCalendarFragment fragmentActivity) {
        super(fragmentActivity);
    }

    //Each Page
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = position;
        return MonthCalendarFragment.newInstance(year, month); //Fragment Instance
    }

    @Override
    public int getItemCount() {
        return 12; //수정해야됨 **************************
    }

}

