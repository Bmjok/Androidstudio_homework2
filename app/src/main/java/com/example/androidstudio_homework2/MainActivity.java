package com.example.androidstudio_homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new First_Fragment());
        fragmentTransaction.commit();

        Calendar cal = Calendar.getInstance();

    }

    public void onTitleSelected(int year, int month, int day, View view) {

        Toast.makeText(getApplicationContext(),"position="+day,Toast.LENGTH_SHORT).show();
        //아직 수정중
    }

}