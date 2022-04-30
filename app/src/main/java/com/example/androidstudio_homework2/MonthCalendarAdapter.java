package com.example.androidstudio_homework2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarAdapter extends BaseAdapter {

    private Context Context;
    private int Resource;
    private ArrayList<String> Days = new ArrayList<String>();
    Calendar cal;
    int I;

    public MonthCalendarAdapter(Context context, int resource, ArrayList<String> days, int i) {
        Context = context;
        Resource = resource;
        Days = days;
        I = i;
    }

    @Override
    public int getCount() {
        return Days.size();
    }

    @Override
    public Object getItem(int position) {
        return Days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(Resource, parent,false);
        }

        TextView day = (TextView) convertView.findViewById(R.id.gridview_day);
        day.setText(Days.get(position));

        return convertView;
    }

}

