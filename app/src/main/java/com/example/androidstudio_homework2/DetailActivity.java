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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 0;
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
    int hour;
    private Inflater inflater;

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
        month = intent.getIntExtra("month",0);
        day = intent.getIntExtra("day", 0);
        hour = intent.getIntExtra("hour", 0);
        title.setText(year + "년" + month + "월" + day + "일" + hour + "시");
        show_day = year + "년" + month + "월" + day + "일";
        //(MonthCalendarFragment)Cursor cursor = mDbHelper.getUserByDayOfSQL(year,(month+1),date2);
        String schedule = intent.getStringExtra("title");

        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        Button delete = (Button)findViewById(R.id.delete);

        if(schedule != null) { //이미 저장된 데이터

            Cursor cursor = mDbHelper.getUserByTitleOfSQL(schedule);
            cursor.moveToFirst();

            if( cursor != null && cursor.moveToFirst() ){
                title.setText(cursor.getString(2));
                int startDate = Integer.parseInt(cursor.getString(3));
                int endDate = Integer.parseInt(cursor.getString(4));
                address.setText(cursor.getString(5));
                memo.setText(cursor.getString(6));
                startPicker.setHour(startDate);
                endPicker.setHour(endDate);
            }

            save.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    //updateRecord();
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "이미 저장된 일정입니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() { //이미 저장->삭제 가능
                @Override
                public void onClick(View view) {
                    //https://saeatechnote.tistory.com/
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                    dialog.setTitle("삭제").setMessage("삭제하시겠습니까?");
                    dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteRecord();
                            Toast.makeText(DetailActivity.this.getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //아무 일도 일어나지 않음
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
            });

        }
        else { //데이터를 처음 추가할 때

            save.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    insertRecord(); //데이터 업데이트
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "취소되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //삭제 없이 바로 종료
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "존재하지 않는 일정입니다", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }
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

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void updateRecord() {
//        Integer.toString(startPicker.getHour());
//        String start_day_hour = Integer.toString(startPicker.getHour());
//        String finish_day_hour = Integer.toString(endPicker.getHour());
//        mDbHelper.updateUserBySQL(title.getText().toString(),
//                start_day_hour,finish_day_hour,address.getText().toString(),memo.getText().toString());
//    }

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
                mapFragment.getMapAsync(DetailActivity.this::onMapReady);

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

        findLocation.setOnClickListener(new Button.OnClickListener() { //찾기 버튼
            Address bestResult;
            @Override
            public void onClick(View view) {
                try {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                    String input = address.getText().toString();
                    List<Address> addresses = geocoder.getFromLocationName(input,1);
                    if (addresses.size() >0) {
                        bestResult = (Address) addresses.get(0);
                    }
                } catch (IOException e) {
                    Log.e(getClass().toString(),"Failed in using Geocoder.", e);
                    return;
                }
                Double latitude = bestResult.getLatitude();
                Double longitude = bestResult.getLongitude();

                LatLng point = new LatLng(latitude,longitude);
                MarkerOptions mOptions = new MarkerOptions();
                mOptions.title("search result");
                googleMap.addMarker(
                        new MarkerOptions().
                                position(point).
                                title(""));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });

    }

}