package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class MainActivity extends AppCompatActivity {

    String htmlCode = "<center><h2>Apple Go</h2>"+
            "<p>The goal of this game is to get as much score as possible.Apple can only eat out it's enemy--Android, leave other friendly applications such as Adobe PS, XD along!</p>" +
            "<p> This is very simple game, tap the screen, apple will fly higher otherwise it will fall in a constant speed.</p>"+
            "<p>each time you ate applications other than android, you lose one life</p>"+
            "This description is set just for now, I still thinking about the rules !!!!!! ";

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HtmlTextView htmlTextView = findViewById(R.id.html_text);
        htmlTextView.setHtml(htmlCode,new HtmlAssetsImageGetter(htmlTextView));
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent  = new Intent(MainActivity.this,NameActivity.class);
                startActivity(startIntent);
            }
        }
        );

    }
}
