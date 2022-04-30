package com.example.androidstudio_homework2;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonthViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonthViewFragment extends Fragment {

    ArrayList<String> days = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    int year;
    int month;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";

    public MonthViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year Parameter 1.
     * @param month Parameter 2.
     * @return A new instance of fragment MonthViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonthViewFragment newInstance(int year, int month) {
        //매개변수로 년도와 월을 받음
        MonthViewFragment fragment = new MonthViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            year = getArguments().getInt(ARG_PARAM1);
            month = getArguments().getInt(ARG_PARAM2);
        }
        */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_monthview,
                container, false);

        ViewPager2 vpPager = rootView.findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new MonthViewAdapter(this);
        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(month,false);

        // Attach the page change listener inside the activity
        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                year = (cal.get(Calendar.YEAR))+(position/12);
                //월간 달력이니까, position은 달력 한 개를 뜻함.
                //1년은 12월이므로, 포지션 12개를 불러왔다면 12개월을 불러왔으므로 1년이 추가되어야함.
                //인덱스는 0부터 시작하니까 12로 나누는 게 맞음.
                //++근데 이부분은 1월부터 시작이라는 가정이므로 수정이 조금 필요함.
                //year에 대입하는 계산을 수정하거나 position자체를 수정하는 게 좋을듯.
                //++ MonthViewAdapter의 position을 수정하는 게 좋을 것 같다.
                month = position%12;
                //position은 달력 한 개를 뜻하므로 나머지 값을 대입하면 쉽게 불러 올 수 있음(0~11)
                //액션바 타이틀 변경(setTitle()메소드): https://onlyfor-me-blog.tistory.com/196
                ActionBar ab = ((MainActivity)getActivity()).getSupportActionBar();
                ab.setTitle(year+"년"+(month+1)+"월");
                //******** 이대로 두면 안되고, 이동하면 날짜가 바뀌게 수정해야함
            }
        });

        return rootView;
    }
}