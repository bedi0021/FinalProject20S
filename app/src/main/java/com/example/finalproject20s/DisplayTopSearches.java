package com.example.finalproject20s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
 * This class displays the song from top search list along with its lyrics.
 */
public class DisplayTopSearches extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView displayTopSearhSong, displayTopSearchArtist, displayTopSearchLyrics;
    ImageButton Heartbtn;
    Button showLyrics;
    ProgressBar pBar;

    String lyrics;

    SQLiteDatabase db;
    ArrayList<Song> songList = new ArrayList<>();
    long newId = 0;

    private static final String FINAL_PROJECT="Final Project: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_top_searches);

        displayTopSearchLyrics=(TextView)findViewById(R.id.lyricsfromtopsearch);
        displayTopSearchArtist=(TextView)findViewById(R.id.displaytopsearchartist);
        displayTopSearhSong=(TextView)findViewById(R.id.displaytopsearchsong);
        showLyrics=(Button)findViewById(R.id.getlyrics2);
        Heartbtn=(ImageButton)findViewById(R.id.heartButton);
        pBar=(ProgressBar)findViewById(R.id.PBinTop);
        Toolbar tBar =(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(tBar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(DisplayTopSearches.this);

        Intent fromTopSearch = getIntent();
        displayTopSearchArtist.setText(fromTopSearch.getStringExtra("ARTIST"));
        displayTopSearhSong.setText(fromTopSearch.getStringExtra("SONG"));
        String topSearchArtist2=fromTopSearch.getStringExtra("ARTIST");
        String topSearchSong2=fromTopSearch.getStringExtra("SONG");
        SongLyrics netThread=new SongLyrics();
        String tsa=null,tss=null;
        if(topSearchArtist2.contains(" ")){tsa=topSearchArtist2.replace(" ","%20");}else{tsa=topSearchArtist2;}
        if(topSearchSong2.contains(" ")){tss=topSearchSong2.replace(" ","%20");}else{tss=topSearchSong2;}
        netThread.execute("https://api.lyrics.ovh/v1/"+tsa+"/"+tss);

        showLyrics.setOnClickListener(click->{
            displayTopSearchLyrics.setText(getResources().getString(R.string.lyrics11)+lyrics);
            Snackbar.make(showLyrics,getResources().getString(R.string.showinglyrics),Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.hide),v -> {displayTopSearchLyrics.setText(getResources().getString(R.string.lyrics11));})
                    .show();
        });

        Heartbtn.setOnClickListener(click -> {
            loadDataFromDatabase();
            ContentValues newValue = new ContentValues();

            String addArtist = topSearchArtist2;
            String addSong = topSearchSong2;
            String addLyrics = lyrics;
            newValue.put(DatabaseCreator.COL_ARTIST, addArtist);
            newValue.put(DatabaseCreator.COL_SONG, addSong);
            newValue.put(DatabaseCreator.COL_LYRICS, addLyrics);
            // newValue.put(DatabaseCreator.COL_ID, 0);

            newId = db.insert(DatabaseCreator.TABLE_NAME, null, newValue);

            Song newFavSong = new Song(newId, addArtist,addSong,addLyrics);
            songList.add(newFavSong);

            Intent profile = new Intent(DisplayTopSearches.this, FavouriteList.class);
            Toast.makeText(DisplayTopSearches.this, getResources().getString(R.string.Added), Toast.LENGTH_LONG).show();
           });


    }
    /**
     * <p>This method loads the previous data from database that has been created to the list of favourites</p>
     * <p>This doesn;t return anything and even it don't have any parameter. To use this method, we just have to call it.</p>
     *
     * @since 1.0
     */
    private void loadDataFromDatabase() {
        DatabaseCreator dbOpener = new DatabaseCreator(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer

         String[] columns = {DatabaseCreator.COL_ID, DatabaseCreator.COL_ARTIST, DatabaseCreator.COL_SONG, DatabaseCreator.COL_LYRICS};
        Cursor results = db.query(false, DatabaseCreator.TABLE_NAME, columns, null, null, null, null, null, null);

        int lyricsColIndex = results.getColumnIndex(DatabaseCreator.COL_LYRICS);
        int songColIndex = results.getColumnIndex(DatabaseCreator.COL_SONG);
        int artistColIndex = results.getColumnIndex(DatabaseCreator.COL_ARTIST);
        int idColIndex = results.getColumnIndex(DatabaseCreator.COL_ID);

        while (results.moveToNext()) {
            String lyrics = results.getString(lyricsColIndex);
            String song = results.getString(songColIndex);
            String artist = results.getString(artistColIndex);
            long id = results.getLong(idColIndex);

            songList.add(new Song(id, artist, song, lyrics));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sls_menu3, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Intent profile = new Intent(DisplayTopSearches.this, TopSearches.class);
                startActivity(profile);
                break;
            case R.id.favbutton:
                Intent profile2 = new Intent(DisplayTopSearches.this, FavouriteList.class);
                startActivity(profile2);
                break;
            case R.id.helpmenuitem:
                AlertDialog.Builder alert = new AlertDialog.Builder(DisplayTopSearches.this);
                alert.setTitle(getResources().getString(R.string.help));
                alert.setMessage(getResources().getString(R.string.helpinfo2));
                alert.setPositiveButton(getResources().getString(R.string.yes),(dialog, which)->{
                    Toast.makeText(DisplayTopSearches.this,getResources().getString(R.string.glad),Toast.LENGTH_LONG).show();
                });
                alert.setNegativeButton(getResources().getString(R.string.no),(dialog, which)->{
                    Toast.makeText(DisplayTopSearches.this,getResources().getString(R.string.feedback),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
                alert.show();
                 break;
            case R.id.aboutProject:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(DisplayTopSearches.this);
                alert2.setTitle(getResources().getString(R.string.aboutProject));
                alert2.setMessage(getResources().getString(R.string.writtenBy));
                alert2.show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.gotoGDS:

                break;
            case R.id.gotoSMH:

                break;
            case R.id.gotoDSS:

                break;
            case R.id.instrucionForApp:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(DisplayTopSearches.this);
                alert2.setTitle(getResources().getString(R.string.appInstructions));
                alert2.setMessage(getResources().getString(R.string.appInstructions2));
                alert2.setNegativeButton(getResources().getString(R.string.close), (dialog, which) -> {dialog.dismiss();});
                alert2.show();
                break;
            case R.id.AboutApi:
                Intent profile = new Intent(DisplayTopSearches.this, APIview.class);
                startActivity(profile);
                break;
            case R.id.donate:
                AlertDialog.Builder alert4 = new AlertDialog.Builder(DisplayTopSearches.this);
                alert4.setTitle(getResources().getString(R.string.donateProject));
                alert4.setMessage(getResources().getString(R.string.donateProject2));
                LayoutInflater inflater=this.getLayoutInflater();
                View donateView = inflater.inflate(R.layout.activity_edittext,null);
                EditText donate=(EditText)donateView.findViewById(R.id.donatehere);
                alert4.setView(donateView);
                alert4.setNegativeButton(getResources().getString(R.string.DonateIT),(dialog, which) -> {
                    dialog.dismiss();
                    String str =donate.getText().toString();
                    donate.setText("");
                    Toast.makeText(DisplayTopSearches.this, getResources().getString(R.string.thankDonate)+str+" "+getResources().getString(R.string.donation), Toast.LENGTH_LONG).show();
                });
                alert4.show();
                break;


        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }
    /**
     * <p>This inner class is getting the lyrics from lyrics.ovh website in order to be used in our application</p>
     *
     * @since 1.0
     */
    private class SongLyrics extends AsyncTask<String, Integer,String> {
        String lyricsfound;
        @Override
        protected String doInBackground(String... args) {
            try{
                URL url = new URL(args[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader((new InputStreamReader(response)));
                StringBuilder sb = new StringBuilder();

                String line;
                while((line=reader.readLine())!=null){sb.append(line+"\n");}
                String result=sb.toString();

                JSONObject jobj = new JSONObject(result);
                lyricsfound=jobj.getString("lyrics");
                publishProgress(25);
                Thread.sleep(250);
                publishProgress(50);
                Thread.sleep(250);
                publishProgress(75);
                Thread.sleep(100);
                if(lyricsfound.isEmpty()){ Log.v(FINAL_PROJECT, "Lyrics not found from json object!");}
                else{Log.v(FINAL_PROJECT, "Lyrics found from json object!");}

            }catch(SecurityException e) {
                Log.v(FINAL_PROJECT,"Internet Access Denied!!");
            }catch (Exception e){
                e.printStackTrace();
                Log.v(FINAL_PROJECT,"App has been crashed!!");
            }


            return "Done!!";
        }

        @Override
        protected void onPostExecute(String s) {
            lyrics = lyricsfound;
            pBar.setVisibility(View.INVISIBLE);
           }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(values[0]);
        }
    }
}
