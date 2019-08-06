package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    String userName;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScore = findViewById(R.id.highScore);
        Button tryAgainButton = findViewById(R.id.tryAgain);
        Button saveScore = findViewById(R.id.saveButton);

         if (getIntent().hasExtra("userName") && getIntent().hasExtra("score")) {

            userName = getIntent().getExtras().getString("userName");
            System.out.println("result name: "+userName);


            score  = getIntent().getExtras().getString("score");
            System.out.println("result score: "+score);

            scoreLabel.setText(score);

       }
         saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveIntent = new Intent(getApplicationContext(),LeaderBoard.class);

                saveIntent.putExtra("userName",userName);
                saveIntent.putExtra("score",score);
                startActivity(saveIntent);
            }
        });

         tryAgainButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent startGameIntent = new Intent(getApplicationContext(),Game.class);
                 startGameIntent.putExtra("userName",userName);
                 startActivity(startGameIntent);
             }
         });

        }



    }

