package com.example.finalproject20s;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class InputLocationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Shared preference to store latitude and longitude
     */
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_location);


        //This gets the toolbar from the layout:
        Toolbar toolbar = findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("LatLongSharedPref", Context.MODE_PRIVATE);

        EditText latitude = findViewById(R.id.latitude);
        EditText longitude = findViewById(R.id.longitude);
        Button done = findViewById(R.id.done);

        latitude.setText(prefs.getString("LATITUDE",""));
        longitude.setText(prefs.getString("LONGITUDE",""));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(latitude.getText().toString().trim().isEmpty() || longitude.getText().toString().trim().isEmpty()){
                    Toast.makeText(InputLocationActivity.this, R.string.enter_value, Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("LATITUDE", latitude.getText().toString());
                editor.putString("LONGITUDE", longitude.getText().toString());
                editor.apply();

                Intent cityfinder = new Intent(InputLocationActivity.this,CityFinderActivity.class);
                cityfinder.putExtra("LATITUDE", latitude.getText().toString());
                cityfinder.putExtra("LONGITUDE", longitude.getText().toString());
                startActivity(cityfinder);


            }
        });

        findViewById(R.id.favoriteCities).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InputLocationActivity.this,FavCitiesActivity.class);
                startActivity(i);
            }
        });

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    // Needed for the OnNavigationItemSelected interface:
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.instructions:

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.instructions)
                        .setMessage(R.string.instructions_about_app)
                        .create();
                alertDialog.show();

                break;
            case R.id.aboutAPI:

                String url = "https://www.geodatasource.com/web-service";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

                break;
            case R.id.donate:

                // https://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
                final EditText input = new EditText(this);
                input.setHint("$$$");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                AlertDialog alertDialogDonate = new AlertDialog.Builder(this)
                        .setTitle(R.string.please_give)
                        .setMessage(R.string.how_much)
                        .setPositiveButton(R.string.thankyou, null)
                        .setNegativeButton(R.string.cancel, null)
                        .setView(input)
                        .create();
                alertDialogDonate.show();

                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
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
