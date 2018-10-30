package com.example.ki.a10_25;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {


    private long id;
    private Button button1, button2, select;
    private String result;
    private View view;
    private DatePickerDialog dpd;
    private Calendar cal;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        Bundle args=new Bundle();
        HomeFragment fragment=new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void init(){
        id=getActivity().getIntent().getLongExtra("id",id);
        select=(Button) view.findViewById(R.id.select_date);
        button1  =(Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 =(Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(this);
        select.setOnClickListener(this);
        cal=Calendar.getInstance();
        dpd=new DatePickerDialog(getActivity(),listener, cal.get(cal.YEAR), cal.get(cal.MONTH) + 1, cal.get(cal.DATE));
        result="";
    }
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            result=year+"-"+monthOfYear+"-"+dayOfMonth;
            setButtonVisible();

        }


    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        init();
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if(v.getId()==R.id.select_date){
            // Show the date picker dialog fragment
            dpd.show();


        }
        if (v.getId()==R.id.button1){
            intent = new Intent(getActivity(), diaryReadActivity.class );
            intent.putExtra("date",result);
            intent.putExtra("id",id);
            startActivity(intent);
        }
        else if (v.getId()==R.id.button2){
            intent = new Intent(getActivity(), diaryWriteActivity.class);
            intent.putExtra("date",result);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
    public void setResult(String result){
        this.result=result;
    }
    public void setButtonVisible(){
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
    }
    public void setButtonGone(){
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
    }



}
