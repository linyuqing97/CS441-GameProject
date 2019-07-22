package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList userRank;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        userRank = new ArrayList<userInfo>();

        recyclerView = findViewById(R.id.recycleView);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecycleAdapter(userRank);
        recyclerView.setAdapter(myAdapter);

        if(getIntent().hasExtra("userName")) {
            String userName = getIntent().getExtras().getString("userName");
            userInfo info = new userInfo(userName);
            userRank.add(info);
            myAdapter.notifyDataSetChanged();


        }
    }
}
