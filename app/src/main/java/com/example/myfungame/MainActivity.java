package com.example.myfungame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class MainActivity extends AppCompatActivity {

    String htmlCode = "<h2>Super Hero </h2>"+
            "<h2>Super Hero </h2>"+"<h2>Super Hero </h2>"+                       "k asdklf jas" +
            "<h2>Super Hero </h2>"+            "df jakl;'sdf ka'sdk;f as" +
            "<h2>Super Hero </h2>"+
            "<h2>Super Hero </h2>"+            "lkdf al;sdkf as;ldkf a;slkdf ;asldkf as" +
            "<h2>Super Hero </h2>"+            "df kl;a's; dl;askf a's;ldk " +
            "<h2>Super Hero </h2>"+            "akdfl; asdl;kf a" +
            "<h2>Super Hero </h2>"+            "df kasld;fk a;sdlkf as" +
            "<h2>Super Hero </h2>"+            "dfk asl;dk fas" +
            "<h2>Super Hero </h2>"+            "'dpfk al;skdf a" +
            "sdfk al;sdk f" +
            "asdfkl asl</p>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HtmlTextView htmlTextView = findViewById(R.id.html_text);
        htmlTextView.setHtml(htmlCode,new HtmlAssetsImageGetter(htmlTextView));
    }
}
