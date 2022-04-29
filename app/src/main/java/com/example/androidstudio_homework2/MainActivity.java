package com.example.androidstudio_homework2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements MonthCalendarFragment.OnTitleSelectedListener{
    Calendar cal; //달력 받아오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션바 타이틀 변경: https://onlyfor-me-blog.tistory.com/196
        ActionBar ab = getSupportActionBar();
        ab.setTitle(date);

        //기본화면(초기화면) -> 월 달력으로 설정
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new MonthCalendarFragment());
        fragmentTransaction.commit();

    }

    private String month_year(Calendar cal) {
        //간단 날짜 불러오기 everyshare.tistory.com/3 [에브리셰어]
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월");
        String date = format.format(Calendar.getInstance().getTime());
        return date; //현재 년도와 월 반환
    }

    public void onTitleSelected(int i) {
        Toast.makeText(getApplicationContext(), "position"+i, Toast.LENGTH_SHORT).show();
    }

    //앱바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //오버플로우 메뉴
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_month:
                //월 선택 -> 월 달력 출력
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MonthCalendarFragment());
                fragmentTransaction.commit();
                //MonthFragment 불러오기
                return true;
            case R.id.action_week:
                //주 선택 -> 주 달력 출력
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WeekCalendarFragment());
                fragmentTransaction.commit();
                //WeekFragment 불러오기
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}