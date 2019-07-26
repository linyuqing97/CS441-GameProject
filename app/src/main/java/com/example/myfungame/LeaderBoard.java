package com.example.myfungame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LeaderBoard extends AppCompatActivity  {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    public ArrayList<UserInfo>userRank;
    boolean userNameCheck = true;
    Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        Button clearButton = findViewById(R.id.clearButton);
        //userRank = new ArrayList<>();

        loadData();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.scrollToPosition(userRank.size()-1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RecycleAdapter(userRank);
        recyclerView.setAdapter(myAdapter);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRank.clear();
                myAdapter.notifyDataSetChanged();
            }
        });

        if (getIntent().hasExtra("userName")) {
            System.out.println("new user"+ userRank.size());
            String userName = getIntent().getExtras().getString("userName");


            //Creating some UserInfos for test
            for(int i = 0;i<20;i++) {
                UserInfo info = new UserInfo(userName+i, "Apple go"+i, (0+i)%5);
                userRank = showTopScore(info);
                myAdapter.notifyDataSetChanged();
            }






        }

    }
    public void notifyDataChanged(){
        myAdapter.notifyDataSetChanged();
    }


    public void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userRank);
        editor.putString("Input list", json);
        editor.apply();
    }

    public ArrayList<UserInfo> showTopScore (UserInfo temp){

        if (userRank.size()>20){
            userRank.remove(20);
            userRank.add(temp);
        }
        else{
            userRank.add(temp);
        }
        LeaderBoardShorter leaderBoardShorter = new LeaderBoardShorter(userRank);
        return leaderBoardShorter.getSortedLeaderBoard();



    }



    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String  json = sharedPreferences.getString("Input list", null);
        Type type = new TypeToken<ArrayList<UserInfo>>() {
        }.getType();
        userRank = gson.fromJson(json, type);

        if (userRank == null) {
            userRank = new ArrayList<>();
        }
    }
}
