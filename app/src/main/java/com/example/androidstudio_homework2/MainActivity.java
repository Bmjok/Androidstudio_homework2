package com.example.androidstudio_homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MonthCalendarFragment.OnTitleSelectedListener{
    ArrayList<MonthGridAdapter> items = new ArrayList<MonthGridAdapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance(); //달력 받아오기

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
                fragmentTransaction.replace(R.id.fragment_container, new MonthGridFragment());
                fragmentTransaction.commit();
                //MonthFragment 불러오기
                return true;
            case R.id.action_week:
                //주 선택 -> 주 달력 출력
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new WeekGridFragment());
                fragmentTransaction.commit();
                //WeekFragment 불러오기
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}