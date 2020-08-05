package com.example.finalproject20s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
 * This class saves a fragment in order to be displayed on clicking the song in favourite list.
 */
public class FragmentMade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        DetailFragment dfrag = new DetailFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FL,dfrag)
                .commit();
    }
}
