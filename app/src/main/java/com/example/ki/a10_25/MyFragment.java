package com.example.ki.a10_25;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    private DatePicker datePicker;//날짜를 선택하는 달력
    private Button button1;
    private Button button2;
    private long id;

    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance(){
        Bundle args=new Bundle();

        MyFragment fragment=new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

}
