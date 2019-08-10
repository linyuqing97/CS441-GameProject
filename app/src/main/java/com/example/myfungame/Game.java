package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {
    public GameView gameView;
    private Handler handler = new Handler();
    private final static long TimerInterval= 30;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = new GameView(this);
        setContentView(gameView);
        if (getIntent().hasExtra("userName")) {
             userName = getIntent().getExtras().getString("userName");
        }

            final Timer timer= new Timer();
            timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        gameView.invalidate();}


//                    }
                });
                    synchronized (gameView){

                    if(gameView.lifeCount==0){
                        timer.cancel();
                        Intent startResultIntent = new Intent(getApplicationContext(),Result.class);
                        startResultIntent.putExtra("userName",userName);
                        String score = Integer.toString(gameView.score);
                        startResultIntent.putExtra("score",score);
                        startActivity(startResultIntent);

                    }
                }
            }

        },0,TimerInterval);


    }
}
