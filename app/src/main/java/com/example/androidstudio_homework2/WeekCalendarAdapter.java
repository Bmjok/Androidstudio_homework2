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

    Context context;
    int mResource;
    ArrayList<String> days;
    Calendar cal;
    int i;

    public WeekCalendarAdapter(Context context)
    {
        this.context = context;
    }

    public WeekCalendarAdapter(Context context,ArrayList<String> days){
        this.context =context;
        this.days=days;
    }

    public WeekCalendarAdapter(Context context, int resource, ArrayList<String> days){
        this.context=context;
        this.mResource=resource;
        this.days=days;
    }
    public WeekCalendarAdapter(Context context, int resource, ArrayList<String> days, int i){
        this.context=context;
        this.mResource=resource;
        this.days=days;
        this.i=i;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
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
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent,false);
        }

        TextView day = (TextView) convertView;
        day.setText(days.get(position)); //text<->string
        day.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.TOP);
        //출처:https://arabiannight.tistory.com/60
        day.setBackground(ContextCompat.getDrawable(
                context, R.drawable.gridview_selet
        ));
        //오류가 생겼던 이유: 월간 달력에서 만들어 왔던 걸 주간에도 그대로 썼었네요...
        //GridView.LayoutParams params = new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, I);
        //day.setLayoutParams(params);
        //당연히 안됐었어요...
        //이부분 수정했습니다!

        return day;
    }
}
