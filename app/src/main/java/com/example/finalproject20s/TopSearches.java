package com.example.finalproject20s;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TopSearches extends AppCompatActivity {

    EditText artist,songName;
    SearchView googleSearch;
    Button googleButton, lyricsSearch, favButton,helpButton;
    ListView topSearchesListView;
    ProgressBar pBar;

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
        googleSearch=(SearchView)findViewById(R.id.googleSearch);
        googleButton=(Button)findViewById(R.id.googlebtn);
        lyricsSearch=(Button)findViewById(R.id.lyricssearchbtn);
        favButton=(Button)findViewById(R.id.gotofavs);
        helpButton=(Button)findViewById(R.id.helpbtn);
        topSearchesListView =(ListView)findViewById(R.id.topsearcheslist);
        pBar=(ProgressBar)findViewById(R.id.progressBar);


        prefs=getSharedPreferences("Song search", Context.MODE_PRIVATE);
        String savedSongString = prefs.getString("ReserveSongSearch","roxanne");
        songName.setText(savedSongString);

        prefs2=getSharedPreferences("Artist search", Context.MODE_PRIVATE);
        String savedArtistString = prefs2.getString("ReserveArtistSearch","police");
        artist.setText(savedArtistString);


        helpButton.setOnClickListener(btn -> {
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

        });

        //Adding random artist and songs
        addRandomTopSerachedSongs(0,"Billie eilish","Ocean eyes");
        addRandomTopSerachedSongs(1,"One direction","One thing");
        addRandomTopSerachedSongs(2,"Ed sheeran","Shape of you");
        addRandomTopSerachedSongs(3,"Shawn mendes","Treat you better");
        addRandomTopSerachedSongs(4,"Ed sheeran","Perfect");
        addRandomTopSerachedSongs(5,"Maroon 5","Memories");
        addRandomTopSerachedSongs(6,"Maroon 5","Girls like you");
        addRandomTopSerachedSongs(7,"Marshmello"," Friends");
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
            //saveSharedPrefs();
            String artistN=artist.getText().toString();
            String songN=songName.getText().toString();
            Intent profile = new Intent(TopSearches.this,DisplaySearchedSong.class);

            profile.putExtra("SEARCHEDARTIST",artistN);
            profile.putExtra("SEARCHEDSONG",songN);

            startActivity(profile);
        });

    }

    private void addRandomTopSerachedSongs(int index, String randomArtist, String randomSong){
        Song sng = new Song(randomArtist,randomSong);
        topSearchesList.add(index,sng);

    }

    private Song getTopSearchItemById(int position){
        Song songstr = topSearchesList.get(position);
        return songstr;
    }


    private void saveSharedPrefs(String songToSave, String artistToSave){
        SharedPreferences.Editor editor2 = prefs.edit();
        editor2.putString("ReserveSongSearch",songToSave);
        editor2.commit();

        SharedPreferences.Editor editor = prefs2.edit();
        editor.putString("ReserveArtistSearch",artistToSave);
        editor.commit();

    }
}
