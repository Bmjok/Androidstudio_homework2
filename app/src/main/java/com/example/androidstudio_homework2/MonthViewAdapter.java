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
        //https://furang-note.tistory.com/29
        position = position-(NUM_ITEMS/2)+month; //캘린더 위치 식별을 편하게 하기 위해
        //position의 기준이 현재 년도의 현재 월이 될 수 있도록 함
        //https://johngrib.github.io/wiki/java-remainder-operator/
        //몫과 나머지가 마이너스로 출력될 수 있음
        int sp_m = position%12;
        int sp_y = position/12;
        int p_year;
        int p_month;
        //스와이프된 년과 월
        if(sp_m<0) { //왼쪽으로 스와이프해서 년도가 바뀌었을 때 (22년 5월 기준으로 == position이 -46 부터 -1까지)
            p_year = year+sp_y-1;
            p_month = 12+sp_m;
        }
        else { //오른쪽으로 스와이프 or 왼쪽으로 스와이프 했는데 년도는 그대로 일 때(현재 1월이 아닌 경우)
            //22년 5월 기준으로 == position이 0부터 54까지
            p_year = year+sp_y;
            p_month = sp_m;
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
