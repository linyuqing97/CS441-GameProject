package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;


import java.util.ArrayList;

public class NameActivity extends AppCompatActivity {

    EditText editText;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent  = new Intent(getApplicationContext(),LeaderBoard.class);
                startIntent.putExtra("userName",editText.getText().toString());
                startActivity(startIntent);

            }


        });
    }

}
