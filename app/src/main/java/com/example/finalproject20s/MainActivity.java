package com.example.finalproject20s;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button GDSbutton, SMHbutton, SLSbutton, DSSbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_button_page);

        GDSbutton=(Button)findViewById(R.id.GDSbtn);
        SMHbutton=(Button)findViewById(R.id.SMHbtn);
        SLSbutton=(Button)findViewById(R.id.SLSbtn);
        DSSbutton=(Button)findViewById(R.id.DSSbtn);

        GDSbutton.setOnClickListener(btn -> {
            //Geo Data sourse
            //Add your respective Activity
        });

        SMHbutton.setOnClickListener(btn -> {
            //Soccer Match Highlights
            //Add your respective Activity
        });

        SLSbutton.setOnClickListener(btn -> {
            //Song Lyrics Search
            //Add your respective Activity
            Intent profile = new Intent(MainActivity.this, TopSearches.class);
            startActivity(profile);
        });

        DSSbutton.setOnClickListener(btn -> {
            //Deezer Song Search
            //Add your respective Activity
        });


    }
}
