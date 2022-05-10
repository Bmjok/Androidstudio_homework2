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
        position = position-(NUM_ITEMS/2)+month; //캘린더 위치 식별을 편하게 하기 위해
        int sp_m = position%12; //month (0~11)
        int sp_y = position/12; //year (...-2 -> -1 -> 0 (현재) -> 1 -> 2...)
        int p_year;
        int p_month;
        //스와이프된 년과 월
        if(sp_m<0) { //왼쪽으로 스와이프해서 년도가 바뀌었을 때
            //나머지가 마이너스 나올 수 있음
            //https://johngrib.github.io/wiki/java-remainder-operator/
            p_year = year+sp_y-1;
            p_month = 12+sp_m;
        }
        else { //오른쪽으로 스와이프 or 왼쪽으로 스와이프 했는데 년도는 그대로 일 때(현재 1월이 아닌 경우)
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
