package com.example.androidstudio_homework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Calendar;

public class MonthViewAdapter extends FragmentStateAdapter {
    //스와이프 동작에 따른 어댑터
    //중요한건 month인듯?
    private static int NUM_ITEMS=100; //스와이프 횟수는 넉넉하게 100번 잡음(더 커도 괜찮을듯...)
    private int year;
    private int month;

    public MonthViewAdapter(Fragment fa) {
        super(fa);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH); //month는 0부터시작하긴 하는데 밑에 계산을 위해서 index를 하나 적게 만들자
    } //year와 month를 현재 날짜로 초기화 (초기 화면은 현재여야하니까, 현재에 기준점을 두고 계산하기)

    // 각 페이지를 나타내는 프래그먼트 반환
    // 각 페이지가 넘어갈 때마다 어떻게 해야 알맞게 년도와 월이 넘어갈까?
    @Override
    public Fragment createFragment(int position) { // ***** 수정필요
        int p_year;
        int p_month;
        //현재가 5월이면, 스와이프를 '왼쪽으로'
        //총 몇 번 해야 이전해로 넘어가는 건가? => 5번(인덱스보다 1 더 감소)
        if(/*왼쪽으로 스와이프*/) { //감소해야함(음수?)
            p_year = year-((month-(s+1))/12);
            p_month = (month-s);
        }
        //현재가 5월이면, 스와이프를 '오른쪽으로'
        //총 몇 번 해야 다음해로 넘어가는 건가? => 12(인덱스로하면1월)-4(5월), 8번 해야 넘어간다.
        //year는? 현재 년도 +@는 확실함.
        //@는? 현재 월+스와이프 횟수가 12로 나눴을 때 몫에 맞게 증가하면 될듯.
        else { //오른쪽으로 스와이프(증가해야함)
            p_year = year+((month+position)/12);
            p_month = month+position;
        }
        //MonthCalendarFragment의 Instance메소드를 통해 스와이프 한 year과 month 반환
        return MonthCalendarFragment.newInstance(p_year, p_month);
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}
