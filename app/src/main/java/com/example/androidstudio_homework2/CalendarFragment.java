package com.example.androidstudio_homework2;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class CalendarFragment extends Fragment {

    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        GridView gridView = rootView.findViewById(R.id.gridview);

        gridView.setAdapter(
                new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_activated_1,
                        data));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Activity activity = getActivity();

                if (activity instanceof OnTitleSelectedListener)
                    ((OnTitleSelectedListener)activity).onTitleSelected(position);
            }
        });
        return rootView;
    }

    public interface OnTitleSelectedListener {
        public void onTitleSelected(int i);
    }

}