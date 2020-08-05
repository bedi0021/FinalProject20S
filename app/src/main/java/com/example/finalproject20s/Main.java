package com.example.finalproject20s;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MatchAdapter mAdapter;
    private RecyclerView rvMatches;
    private ArrayList<MatchModel> mArrMatches =  new ArrayList<>();;
    private ProgressDialog mProgressDialog;
    private static String url = "https://www.scorebat.com/video-api/v1";
    JSONArray list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(tbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,tbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        new MatchDownloads().execute();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvMatches = (RecyclerView) findViewById(R.id.rv_resources);
        LinearLayoutManager llm = new LinearLayoutManager(Main.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMatches.setLayoutManager(llm);
        mProgressDialog= new ProgressDialog(Main.this, R.style.App_DialogStyle);
        mProgressDialog.setMessage("Loading");
        //getMatches();
        mAdapter=new MatchAdapter(mArrMatches, Main.this);
        rvMatches.setAdapter(mAdapter);

    }

    /**
     * this method displays the toolbar of the page that shows list of matches
     * @param menu
     * @return
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * the method is used to go to another class AboutActivity ehich displays infoprmation of the project developer
     * @param item
     * @return
     */


    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch (item.getItemId()) {
            case R.id.item1:{
                message = "About Application";
                startActivity(new Intent(Main.this,AboutActivity.class));
            }
            break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    public class MatchDownloads extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
           // swipeRefresh.setRefreshing(true);
        }

    @Override
    protected String doInBackground(String... params) {
        mArrMatches.clear();
        String result = null;
        try {
            URL url = new URL("https://www.scorebat.com/video-api/v1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String match;

                while ((match = reader.readLine()) != null) {
                    stringBuilder.append(match);
                }
                result = stringBuilder.toString();
            }else  {
                result = "error";
            }

        } catch (Exception  e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onPostExecute(String s) {
        super .onPostExecute(s);
       // swipeRefresh.setRefreshing(false);
        try {
            JSONArray array =  new JSONArray(s);

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                String title = jsonObject.getString("title");
                String embed = jsonObject.getString("embed");
                String url = jsonObject.getString("url");
                String thumbnail = jsonObject.getString("thumbnail");
                String date = jsonObject.getString("date");

                JSONObject side1Object = jsonObject.getJSONObject("side1");
                String side1Name = side1Object.getString("name");
                String side1Url = side1Object.getString("url");

                Side1Model side1Model = new Side1Model(side1Name,side1Url);

                JSONObject side2Object = jsonObject.getJSONObject("side2");
                String side2Name = side2Object.getString("name");
                String side2Url = side2Object.getString("url");

                Side2Model side2Model = new Side2Model(side2Name,side2Url);

                JSONObject competition = jsonObject.getJSONObject("competition");
                String competitionName = competition.getString("name");
                int competitionId = competition.getInt("id");
                String competitionUrl = competition.getString("url");

                CompetitionModel competitionModel = new CompetitionModel(competitionName,competitionId,competitionUrl);

                JSONArray videos = jsonObject.getJSONArray("videos");
                ArrayList<VideoModel> videoModelList = new ArrayList<>();
                for (int j = 0; j < videos.length(); j++) {
                    JSONObject videoObject = array.getJSONObject(i);
                    String videoTitle = videoObject.getString("title");
                    String videoEmbed = videoObject.getString("embed");

                    VideoModel videoModel = new VideoModel(videoTitle,videoEmbed);
                    videoModelList.add(videoModel);

                }


                MatchModel model = new MatchModel(title,embed,url,thumbnail,date,side1Model,side2Model,competitionModel,videoModelList);

                mArrMatches.add(model);
            }
        } catch (JSONException  e) {
            e.printStackTrace();
        }
        rvMatches.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

      //  CustomAdapter adapter = new CustomAdapter(MainActivity.this, arrayList);
      //  listView.setAdapter(adapter);

    }
}


    /**
     * navigates to the other activity known as FavouriteaActivity
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemnav2:
                startActivity(new Intent(Main.this, FavouritesActivity.class));
                break;
        }
        return true;
    }
}
