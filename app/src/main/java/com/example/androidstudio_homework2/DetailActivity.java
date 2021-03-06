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

        //????????? ?????? ??????
        ActionBar ab = getSupportActionBar();
        ab.setTitle("CalendarApp");

        //DB ??????
        mDbHelper = new DBHelper(this);
        title = findViewById(R.id.title);
        startPicker = findViewById(R.id.start_day);
        endPicker = findViewById(R.id.finish_day);
        findLocation = findViewById(R.id.findLocation);
        address = findViewById(R.id.address);
        memo = findViewById(R.id.memo);

        //????????? ??????, ??? ??? ??? ??????
        Intent intent = getIntent();
        year = intent.getIntExtra("year",0);
        month = intent.getIntExtra("month",0);
        day = intent.getIntExtra("day", 0);
        hour = intent.getIntExtra("hour", 0);
        title.setText(year + "???" + month + "???" + day + "???" + hour + "???");
        show_day = year + "???" + month + "???" + day + "???";
        //(MonthCalendarFragment)Cursor cursor = mDbHelper.getUserByDayOfSQL(year,(month+1),date2);
        String schedule = intent.getStringExtra("title");

        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        Button delete = (Button)findViewById(R.id.delete);

        if(schedule != null) { //?????? ????????? ?????????

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
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "?????? ????????? ???????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() { //?????? ??????->?????? ??????
                @Override
                public void onClick(View view) {
                    //https://saeatechnote.tistory.com/
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                    dialog.setTitle("??????").setMessage("?????????????????????????");
                    dialog.setPositiveButton("???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteRecord();
                            Toast.makeText(DetailActivity.this.getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    dialog.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //?????? ?????? ???????????? ??????
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
            });

        }
        else { //???????????? ?????? ????????? ???

            save.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    insertRecord(); //????????? ????????????
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //?????? ?????? ?????? ??????
                    Toast.makeText(DetailActivity.this.getApplicationContext(), "???????????? ?????? ???????????????", Toast.LENGTH_SHORT).show();
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
        // 1. ?????? ????????? ????????? ?????? ?????? ??? ??????
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    DetailActivity.this,            // DetailActivity ??????????????? ?????? ??????????????? ?????????
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // ????????? ?????? ????????? ????????? String ??????
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // ????????? ?????? int ??????. ?????? ?????? ????????? ?????? ???
            );
            return;
            //***????????? ????????? ??? ?????????????????? ????????? ?????? ??????
        }

        // 2. Task<Location> ?????? ??????
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task??? ??????????????? ?????? ??? ???????????? OnSuccessListener ??????
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mLocation = location; //????????????
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(DetailActivity.this::onMapReady);

                // 4. ??????????????? ????????? ??????(location ??????)??? ??????.
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
        //googlemap ????????? ???????????? ?????? ??????(mLocation)??? ??????
        LatLng nowLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        //latitude, longitude == ???????????? ????????????
        // move the camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowLocation, 15));

        findLocation.setOnClickListener(new Button.OnClickListener() { //?????? ??????
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