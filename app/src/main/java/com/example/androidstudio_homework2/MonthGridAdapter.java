package com.example.androidstudio_homework2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthGridAdapter extends BaseAdapter {
    private Context Context;
    private int Resource;
    private ArrayList<String> Items = new ArrayList<String>();
    Calendar cal = Calendar.getInstance();

    public MonthGridAdapter (Context context, int resource, ArrayList<String> items) {
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
        return null; //수정 필요 *****************************
    }

}
