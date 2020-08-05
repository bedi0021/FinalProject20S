package com.example.finalproject20s;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * This application helps you to find lyrics for the popular songs around he world
 * Along with doing a google search for the desired song
 *
 * @author Harmanpreet Bedi
 * @version 1.0
 * @since 2020-05-08
 *
 */

/**
 * This class loads the webpage for the google search for any particular song
 */
public class GoogleWebView extends AppCompatActivity {
String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_web_view);

        Intent fromTopSearch = getIntent();
        str=fromTopSearch.getStringExtra("GOOGLE");
        WebView browser =(WebView)findViewById(R.id.googleWeb);
        String s;
        if(str.contains(" ")){s = str.replace(" ","+");}else{s=str;}
        browser.loadUrl("https://www.google.com/search?q="+str);
    }
}
