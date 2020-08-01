package com.example.finalproject20s;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DisplaySearchedSong extends AppCompatActivity {

    TextView displaysearchedartist,displaysearchedsong,displaysearchedlyrics;
    ImageButton heartbtn;
    Button helpbtn, showLyrics;
    ProgressBar pBar;

    String SongN, ArtistN;
    String Gotlyrics;

    private static final String FINAL_PROJECT="Final Project: ";

    SearchedSongLyrics netThread = new SearchedSongLyrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_searched_song);

        displaysearchedartist=(TextView)findViewById(R.id.displaysearchedartist);
        displaysearchedsong=(TextView)findViewById(R.id.displaysearchedsong);
        displaysearchedlyrics=(TextView)findViewById(R.id.lyricsgotten);
        helpbtn=(Button)findViewById(R.id.helpindisplay2);
        pBar=(ProgressBar)findViewById(R.id.PBinSearched);
        showLyrics=(Button)findViewById(R.id.getlyrics);

        Intent fromTopSearch2=getIntent();
        displaysearchedartist.setText(fromTopSearch2.getStringExtra("SEARCHEDARTIST"));
        displaysearchedsong.setText(fromTopSearch2.getStringExtra("SEARCHEDSONG"));
        //displaysearchedlyrics.setText(lyrics);

        SongN=fromTopSearch2.getStringExtra("SEARCHEDSONG");
        ArtistN=fromTopSearch2.getStringExtra("SEARCHEDARTIST");

        String tsa=null, tss=null;
        if(ArtistN.contains(" ")){tsa=ArtistN.replace(" ","%20");}else{tsa=ArtistN;}
        if(SongN.contains(" ")){tss=SongN.replace(" ","%20");}else{tss=SongN;}
        netThread.execute("https://api.lyrics.ovh/v1/"+tsa+"/"+tss);

        showLyrics.setOnClickListener(click->{
            String lyr= Gotlyrics;
            displaysearchedlyrics.setText("Lyrics:\n"+lyr.toString()+" End!");
            Snackbar.make(showLyrics,"Showing lyrics",Snackbar.LENGTH_LONG)
                    .setAction("hide",v -> {displaysearchedlyrics.setText("Lyrics: ");})
                    .show();
        });
       // displaysearchedlyrics.setText("Lyrics:\n"+lyrics);


        helpbtn.setOnClickListener(click -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(DisplaySearchedSong.this);
            alert.setTitle(getResources().getString(R.string.help));
            alert.setMessage(getResources().getString(R.string.helpinfo2));
            alert.setPositiveButton(getResources().getString(R.string.yes),(dialog, which)->{
                Toast.makeText(DisplaySearchedSong.this,getResources().getString(R.string.glad),Toast.LENGTH_LONG).show();
            });
            alert.setNegativeButton(getResources().getString(R.string.no),(dialog, which)->{
                Toast.makeText(DisplaySearchedSong.this,getResources().getString(R.string.feedback),Toast.LENGTH_LONG).show();
                dialog.dismiss();
            });
            alert.show();
        });
    }
    private class SearchedSongLyrics extends AsyncTask<String, Integer,String> {
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
                //Thread.sleep(250);
                publishProgress(50);
                //Thread.sleep(250);
                publishProgress(75);
               // Thread.sleep(100);
                if(lyricsfound.isEmpty()){
                Log.v(FINAL_PROJECT, "Lyrics not found from json object!");}
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
            Gotlyrics = lyricsfound;
            pBar.setVisibility(View.INVISIBLE);
           // Log.v(FINAL_PROJECT,lyrics);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(values[0]);
        }
    }
}
