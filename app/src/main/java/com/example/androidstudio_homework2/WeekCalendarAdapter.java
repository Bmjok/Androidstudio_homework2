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

public class WeekCalendarAdapter extends BaseAdapter {
    android.content.Context Context;
    int Resource;
    int sResource;
    ArrayList<String> Days;
    Calendar cal;
    int I;

    public WeekCalendarAdapter(Context context, int s_resource) {
        Context = context;
        sResource = s_resource;
    }

    public WeekCalendarAdapter(Context context, int s_resource, ArrayList<String> days) {
        Context = context;
        sResource = s_resource;
        Days = days;
    }

    public WeekCalendarAdapter(Context context, int s_resource, ArrayList<String> days, int i) {
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

    @Override
    public int getCount() {
        return Days.size();
    }

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
            convertView = inflater.inflate(sResource, parent,false);
        }

        TextView day = (TextView) convertView;
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
