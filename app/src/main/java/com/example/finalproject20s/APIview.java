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
 * This class helps to load url for API information for
 * the lyrics website.
 *
 */

public class APIview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_view);
        String str = "https://lyricsovh.docs.apiary.io/#";
        WebView browser =(WebView)findViewById(R.id.APIWeb);
        browser.loadUrl(str);

    }
}
