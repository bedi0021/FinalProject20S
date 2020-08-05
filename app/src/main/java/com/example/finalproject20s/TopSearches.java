package com.example.finalproject20s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
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
 * This class loads the first which will be opened after clicking for the desire Song search app
 *
 * @since 1.0
 */
public class TopSearches extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText artist,songName;
    EditText googleSearch;
    Button googleButton, lyricsSearch, favButton,helpButton;
    ListView topSearchesListView;


    SharedPreferences prefs = null, prefs2=null;
    MyListAdapter myTopSearchAdapter;

    ArrayList<Song> topSearchesList = new ArrayList<>();
    String lyrics, topSearchArtist, topSearchSong;
    private static final String FINAL_PROJECT="Final Project";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_searches);

        artist=(EditText)findViewById(R.id.artist);
        songName=(EditText)findViewById(R.id.songname);
        googleSearch=(EditText)findViewById(R.id.googleSearch);
        googleButton=(Button)findViewById(R.id.googlebtn);
        lyricsSearch=(Button)findViewById(R.id.lyricssearchbtn);
        topSearchesListView =(ListView)findViewById(R.id.topsearcheslist);
        Toolbar tBar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);
        googleButton.setOnClickListener(click -> {
            Intent profile2 = new Intent(TopSearches.this, GoogleWebView.class);
            String googlestr = googleSearch.getText().toString();
            profile2.putExtra("GOOGLE",googlestr);
            startActivity(profile2);
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(TopSearches.this);


        prefs=getSharedPreferences("Song search", Context.MODE_PRIVATE);
        String savedSongString = prefs.getString("ReserveSongSearch","roxanne");
        songName.setText(savedSongString);

        prefs2=getSharedPreferences("Artist search", Context.MODE_PRIVATE);
        String savedArtistString = prefs2.getString("ReserveArtistSearch","police");
        artist.setText(savedArtistString);

        //Adding random artist and songs
        addRandomTopSerachedSongs(0,"Billie eilish","Ocean eyes");
        addRandomTopSerachedSongs(1,"One direction","One thing");
        addRandomTopSerachedSongs(2,"Ed sheeran","Shape of you");
        addRandomTopSerachedSongs(3,"Shawn mendes","Treat you better");
        addRandomTopSerachedSongs(4,"Ed sheeran","Perfect");
        addRandomTopSerachedSongs(5,"Maroon 5","Memories");
        addRandomTopSerachedSongs(6,"Maroon 5","Girls like you");
        addRandomTopSerachedSongs(7,"Marshmello"," Friends");
        addRandomTopSerachedSongs(8,"Eminem","Mocking Bird");
        addRandomTopSerachedSongs(9,"Eminem","Rap god");
        addRandomTopSerachedSongs(10,"One direction","Live while we're young");
        addRandomTopSerachedSongs(11,"Dua lipa","New Rules");

        myTopSearchAdapter=new MyListAdapter(this,topSearchesList);
        topSearchesListView.setAdapter(myTopSearchAdapter);

        topSearchesListView.setLongClickable(true);
        topSearchesListView.setOnItemClickListener(((parent, view, position, id) -> {
            Intent profile = new Intent(TopSearches.this, DisplayTopSearches.class);
            int index=position;
            Song songStr= getTopSearchItemById(index);
            topSearchArtist=songStr.getArtist();
            topSearchSong=songStr.getSongname();

            profile.putExtra("ARTIST", topSearchArtist);
            profile.putExtra("SONG", topSearchSong);

            startActivity(profile);
           }));

        lyricsSearch.setOnClickListener(btn -> {
            saveSharedPrefs(songName.getText().toString(),artist.getText().toString());
            String artistN=artist.getText().toString();
            String songN=songName.getText().toString();
            Intent profile = new Intent(TopSearches.this,DisplaySearchedSong.class);

            profile.putExtra("SEARCHEDARTIST",artistN);
            profile.putExtra("SEARCHEDSONG",songN);

            startActivity(profile);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sls_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.favheart:
                Intent profile = new Intent(TopSearches.this, FavouriteList.class);
                startActivity(profile);
                break;
            case R.id.helpmenuitem:
                AlertDialog.Builder alert = new AlertDialog.Builder(TopSearches.this);
                alert.setTitle(getResources().getString(R.string.help));
                alert.setMessage(getResources().getString(R.string.helpinfo));
                alert.setPositiveButton(getResources().getString(R.string.yes),(dialog, which)->{
                    Toast.makeText(TopSearches.this,getResources().getString(R.string.glad),Toast.LENGTH_LONG).show();
                });
                alert.setNegativeButton(getResources().getString(R.string.no),(dialog, which)->{
                    Toast.makeText(TopSearches.this,getResources().getString(R.string.feedback),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
                alert.show();
                break;
            case R.id.aboutProject:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(TopSearches.this);
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
                AlertDialog.Builder alert2 = new AlertDialog.Builder(TopSearches.this);
                alert2.setTitle(getResources().getString(R.string.appInstructions));
                alert2.setMessage(getResources().getString(R.string.appInstructions2));
                alert2.setNegativeButton(getResources().getString(R.string.close), (dialog, which) -> {dialog.dismiss();});
                alert2.show();
                break;
            case R.id.AboutApi:
                Intent profile = new Intent(TopSearches.this, APIview.class);
                startActivity(profile);
                break;
            case R.id.donate:
                AlertDialog.Builder alert4 = new AlertDialog.Builder(TopSearches.this);
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
                    Toast.makeText(TopSearches.this, getResources().getString(R.string.thankDonate)+str+" "+getResources().getString(R.string.donation), Toast.LENGTH_LONG).show();
                });
                alert4.show();
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    /**
     * <p>This method adds some random values to the top search list</p>
     *
     * @param index This will decide the index to be used for the particular item.
     * @param randomArtist This will add the artist name.
     * @param randomSong This will add the song name of that artist.
     *
     * @since 1.0
     */
    private void addRandomTopSerachedSongs(int index, String randomArtist, String randomSong){
        Song sng = new Song(randomArtist,randomSong);
        topSearchesList.add(index,sng);

    }

    /**
     * <p>This method is used is select a particular song at a particular position</p>
     * @param position This will decide the index of the song to be selected
     * @return retuns the song at that particular position.
     * @since 1.0
     */
    private Song getTopSearchItemById(int position){
        Song songstr = topSearchesList.get(position);
        return songstr;
    }


    /**
     * <p>This method will save the value that will be once enterd by the user.</p>
     * @param songToSave this song will be saved after the user use the app for once.
     * @param artistToSave this artist will be saved after the user use the app for once.
     * @since 1.0
     */
    private void saveSharedPrefs(String songToSave, String artistToSave){
        SharedPreferences.Editor editor2 = prefs.edit();
        editor2.putString("ReserveSongSearch",songToSave);
        editor2.commit();

        SharedPreferences.Editor editor = prefs2.edit();
        editor.putString("ReserveArtistSearch",artistToSave);
        editor.commit();

    }
}
