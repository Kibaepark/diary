package com.example.ki.a10_25;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private ImageButton write;
    private ImageButton read;
    private View view;
    private long id;

    public static HomeFragment newInstance(){
        Bundle args=new Bundle();

        HomeFragment fragment=new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);
        initFragment();
        return view;
    }

    private void initFragment(){
        write=view.findViewById(R.id.writeButton);
        read=view.findViewById(R.id.readButton);
        write.setOnClickListener(this);
        read.setOnClickListener(this);
        id=getActivity().getIntent().getLongExtra("id",id);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.writeButton){
            Intent intent =new Intent(getActivity(),DiaryListActivity.class);
            intent.putExtra("flag","1");
            intent.putExtra("id",id);
            startActivity(intent);
        }else{
            Intent intent =new Intent(getActivity(),DiaryListActivity.class);
            intent.putExtra("flag","0");
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
}
