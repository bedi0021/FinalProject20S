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

public class DisplayTopSearches extends AppCompatActivity {

    TextView displayTopSearhSong, displayTopSearchArtist, displayTopSearchLyrics;
    ImageButton Heartbtn;
    Button helpbutton, showLyrics;
    ProgressBar pBar;

    String lyrics;

    private static final String FINAL_PROJECT="Final Project: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_top_searches);

        displayTopSearchLyrics=(TextView)findViewById(R.id.lyricsfromtopsearch);
        displayTopSearchArtist=(TextView)findViewById(R.id.displaytopsearchartist);
        displayTopSearhSong=(TextView)findViewById(R.id.displaytopsearchsong);
        helpbutton=(Button)findViewById(R.id.helpindisplay);
        showLyrics=(Button)findViewById(R.id.getlyrics2);
        pBar=(ProgressBar)findViewById(R.id.PBinTop);

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
            displayTopSearchLyrics.setText("Lyrics:\n"+lyrics);
            Snackbar.make(showLyrics,"Showing lyrics",Snackbar.LENGTH_LONG)
                    .setAction("hide",v -> {displayTopSearchLyrics.setText("Lyrics: ");})
                    .show();
        });

        helpbutton.setOnClickListener(click -> {
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
        });

    }

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
            //Log.v(FINAL_PROJECT,lyrics);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(values[0]);
        }
    }
}
