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

public class First_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public First_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static First_Fragment newInstance(String param1, String param2) {
        First_Fragment fragment = new First_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        GridView gridView = rootView.findViewById(R.id.gridview_cal_tv);

        GridView.setAdapter(
                new ArrayAdapter<String>(
                        getActivity(),  // 현재 프래그먼트 연결된 액티비티
                        android.R.layout./*아이디아직*/,
                        /*데이터원본들어갈거아직안정함*/));  // 데이터 원본
        // 리스트뷰 항목이 선택되었을 때, 항목 클릭 이벤트 처리
        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Activity activity = getActivity();

                // 선택된 항목 위치 (position)을 이 프래그먼트와 연결된 MainActivity로 전달
                if (activity instanceof MainActivity)
                    ((MainActivity)activity).onTitleSelected(position);
            }
        });
        return rootView;
    }
}