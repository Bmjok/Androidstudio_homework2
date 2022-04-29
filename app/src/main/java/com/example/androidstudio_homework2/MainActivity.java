package com.example.androidstudio_homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnTitleSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.fragment_container, new MonthFragment());
        fragmentTransaction.commit();

        Calendar cal = Calendar.getInstance();

    }

    public void onTitleSelected(int i) {
        if (getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            MonthFragment monthFragment = MonthFragment.newInstance(i);
            getSupportFragmentManager().beginTransaction().replace(R.id.details, monthFragment).commit();
        } else {    // 화면 크기가 작은 경우
            Intent intent = new Intent(this, MonthFragment.class);
            intent.putExtra("index", i);
            startActivity(intent);
        }
    }

}