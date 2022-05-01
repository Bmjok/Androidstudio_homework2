package com.example.androidstudio_homework2;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarAdapter extends BaseAdapter {

    Context Context;
    int Resource;
    int sResource;
    ArrayList<String> Days;
    Calendar cal;
    int I;

    public MonthCalendarAdapter(Context context, int s_resource) {
        Context = context;
        sResource = s_resource;
    }

    public MonthCalendarAdapter(Context context, int s_resource, ArrayList<String> days) {
        Context = context;
        sResource = s_resource;
        Days = days;
    }

    public MonthCalendarAdapter(Context context, int s_resource, ArrayList<String> days, int i) {
        Context = context;
        sResource = s_resource;
        Days = days;
        I = i;
    }

//    public MonthCalendarAdapter(Context context, int resource, ArrayList<String> days, int i) {
//        Context = context;
//        Resource = resource;
//        Days = days;
//        I = i;
//    }

    // MonthCalendarAdapter 클래스가 관리하는 항목의 총 개수를 반환
    @Override
    public int getCount() {
        return Days.size();
    }

    // MonthCalendarAdapter 클래스가 관리하는 항목의 중에서 position 위치의 항목을 반환
    @Override
    public String getItem(int position) {
        return Days.get(position);
    }

    // 항목 id를 항목의 위치로 간주함
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day, parent,false);
        }

        TextView day = (TextView) convertView.findViewById(R.id.gridview_day);
        day.setText(Days.get(position)); //text<->string
        day.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.TOP);
        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT, I);
        day.setLayoutParams(params);
        day.setBackground(ContextCompat.getDrawable(
                Context,R.drawable.gridview_selet
        ));

        return day;
    }

}

