package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.applevsandroid);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent  = new Intent(splashScreen.this,MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        }, 3000);
    }
}
