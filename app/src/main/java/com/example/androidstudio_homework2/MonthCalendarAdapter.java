package com.example.androidstudio_homework2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendarAdapter extends BaseAdapter {

    private Context Context;
    private int Resource;
    private ArrayList<MonthCalendarAdapter> Days = new ArrayList<MonthCalendarAdapter>();
    Calendar cal = Calendar.getInstance();

    public MonthCalendarAdapter (Context context, int resource, ArrayList<MonthCalendarAdapter> days) {
        Context = context;
        Resource = resource;
        Days = days;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(Resource, viewGroup,false);
        }

        GridView day = (GridView) view.findViewById(R.id.gridview);

        return view;
    }

}

