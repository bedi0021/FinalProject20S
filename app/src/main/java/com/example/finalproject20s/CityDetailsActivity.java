package com.example.finalproject20s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CityDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        //This gets the toolbar from the layout:
        Toolbar toolbar = findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(toolbar);


        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        CityDetailsFragment dFragment = new CityDetailsFragment(); //add a DetailFragment
        dFragment.setArguments( dataToPass ); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment. Calls onCreate() in DetailFragment


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        //Look at your menu XML file. Put a case for every id in that file:
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.soccerMatch:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.songLyrics:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.dezzerSong:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.aboutProject:
                Toast.makeText(this, R.string.about_project_message, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
