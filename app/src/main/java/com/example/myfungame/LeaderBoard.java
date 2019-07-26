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


            for(int i =0; i<userRank.size();i++){
                if (userName.equalsIgnoreCase( userRank.get(i).getName())){
                    System.out.print("Dupicated");
                    userNameCheck = false;
                }

            }
            if (userNameCheck) {
                UserInfo info = new UserInfo(userName,"Apple go",0);
                userRank.add(info);
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
        ArrayList shortedList = new ArrayList<UserInfo>();
        if(userRank.size()==0)userRank.add(temp);
        else{
            for(int i =0;i<userRank.size()+1;i++){
                if(temp.getPoint()>userRank.get(i).getPoint())shortedList.add(temp);
                else{
                    shortedList.add(userRank.get(i));
                }
            }
        }
        return shortedList;


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
