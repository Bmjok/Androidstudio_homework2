package com.example.androidstudio_homework2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLocation;
    private DBHelper mDbHelper;
    Location mLastLocation;

    EditText title;
    String show_day;
    TimePicker startPicker;
    TimePicker endPicker;
    Button findLocation;
    EditText address ;
    TextView memo;

    int year;
    int month;
    int day;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //액션바 이름 변경
        ActionBar ab = getSupportActionBar();
        ab.setTitle("CalendarApp");

        //DB 연결
        mDbHelper = new DBHelper(this);
        title = findViewById(R.id.title);
        startPicker = findViewById(R.id.start_day);
        endPicker = findViewById(R.id.finish_day);
        findLocation = findViewById(R.id.findLocation);
        address = findViewById(R.id.address);
        memo = findViewById(R.id.memo);

        //인텐트 선언, 년 월 일 받기
        Intent intent = getIntent();
        year = intent.getIntExtra("year",0);
        month =intent.getIntExtra("month",0);
        day = intent.getIntExtra("day",0);
        title.setText(year + "년" + month + "월" + day + "일");

        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                insertRecord();
                Toast.makeText(DetailActivity.this.getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Button cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://saeatechnote.tistory.com/
                AlertDialog.Builder dlg = new AlertDialog.Builder(DetailActivity.this);
                dlg.setTitle("삭제").setMessage("삭제하시겠습니까?");
                dlg.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord();
                        Toast.makeText(DetailActivity.this.getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                dlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //아무 일도 일어나지 않음
                    }
                });
                AlertDialog alertDialog = dlg.create();
                alertDialog.show();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertRecord() {
        Integer.toString(startPicker.getHour());
        String start_day_hour = Integer.toString(startPicker.getHour());
        String finish_day_hour = Integer.toString(endPicker.getHour());
        mDbHelper.insertUserBySQL(show_day, title.getText().toString(),
                start_day_hour,finish_day_hour,address.getText().toString(),memo.getText().toString());
    }

    private void deleteRecord() {
        mDbHelper.deleteUserBySQL(title.getText().toString());
    }

    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetailActivity.this,            // DetailActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
            //***허가를 얻었을 때 처리해야하는 코드가 따로 있음
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mLocation = location; //현재위치
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(DetailActivity.this);

                // 4. 마지막으로 알려진 위치(location 객체)를 얻음.
                if (location != null) {
                    mLastLocation = location;
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No location detected",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public void onMapReady(GoogleMap googleMap) {
        //googlemap 객체를 이용하여 현재 위치(mLocation)를 표시
        LatLng nowLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        //latitude, longitude == 현재위치 받아오기
        // move the camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowLocation, 15));
    }

}