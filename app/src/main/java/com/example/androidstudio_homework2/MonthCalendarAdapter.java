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
    private ArrayList<MonthCalendarAdapter> Items = new ArrayList<MonthCalendarAdapter>();
    Calendar cal = Calendar.getInstance();

    public MonthCalendarAdapter (Context context, int resource, ArrayList<MonthCalendarAdapter> items) {
        Context = context;
        Resource = resource;
        Items = items;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public Object getItem(int position) {
        return Items.get(position);
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

