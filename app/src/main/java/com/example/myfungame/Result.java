package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScore = findViewById(R.id.highScore);
        Button tryAgainButton = findViewById(R.id.tryAgain);
        Button saveScore = findViewById(R.id.saveButton);

    }
}
