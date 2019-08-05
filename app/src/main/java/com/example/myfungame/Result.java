package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String userName;
        String score;


        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScore = findViewById(R.id.highScore);
        Button tryAgainButton = findViewById(R.id.tryAgain);
        Button saveScore = findViewById(R.id.saveButton);
        Intent saveIntent = new Intent(getApplicationContext(),LeaderBoard.class);
        if (getIntent().hasExtra("userName")) {
            userName = getIntent().getExtras().getString("userName");

            saveIntent.putExtra("name",userName);


        }
        if (getIntent().hasExtra("score")) {
            score  = getIntent().getExtras().getString("score");

            scoreLabel.setText(score);
            saveIntent.putExtra("score",score);


        }

        saveScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent saveIntent = new Intent(getApplicationContext(),LeaderBoard.class);
                startActivity(saveIntent);
            }
        });

    }
}
