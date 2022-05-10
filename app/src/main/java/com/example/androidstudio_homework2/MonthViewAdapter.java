package com.example.androidstudio_homework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Calendar;

public class MonthViewAdapter extends FragmentStateAdapter {
    //스와이프 동작에 따른 어댑터
    private static int NUM_ITEMS=100;
    //스와이프 횟수는 넉넉하게 100번 잡음
    //NUM_ITEMS 숫자 변경시 아래 position 값과, MonthViewFragment 내부 setCurrentItem 값도 변경해야 함.
    private int year;
    private int month;

    public MonthViewAdapter(Fragment fa) {
        super(fa);
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
    } //year와 month를 현재 날짜로 초기화

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        //계산 수정할거임!!!!! 왜 딜레이가 걸릴까요?
        //https://furang-note.tistory.com/29
        position = position-(NUM_ITEMS/2); //캘린더 위치 식별을 편하게 하기 위해
        int s_p = (position+month)%12;
        int sp = (position+month)/12;
        int p_year;
        int p_month;
        //스와이프된 년과 월
        if(s_p<0) { //왼쪽으로 스와이프해서 년도가 바뀌었을 때
            p_year = year+sp-1;
            p_month = 12+s_p;
        }
        else { //오른쪽으로 스와이프 or 왼쪽으로 스와이프 했는데 년도는 그대로 일 때(현재 1월이 아닌 경우)
            p_year = year+sp;
            p_month = s_p;
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
