package com.example.androidstudio_homework2;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);
        GridView gridView = rootView.findViewById(R.id.grid_calendar);

        GridView.setAdapter(
                new ArrayAdapter<String>(
                        getActivity(),  // 현재 프래그먼트 연결된 액티비티
                        android.R.layout.grid_calendar,
                        data));  // 데이터 원본
        // 그리드뷰 항목이 선택되었을 때, 항목 클릭 이벤트 처리
        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Activity activity = getActivity();

                // 선택된 항목 위치(position)을 OnTitleSelectedListener 인터페이스를 구현한 액티비티로 전달
                if (activity instanceof MonthFragment.OnTitleSelectedListener)
                    ((MonthFragment.OnTitleSelectedListener)activity).onTitleSelected(cal.get(MONTH), cal.get(DAY_OF_WEEK));

            }
        });
        return rootView;
    }
}