package com.example.finalproject20s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CityFinderActivity extends AppCompatActivity {

    ArrayList<City> citylist = new ArrayList<>();
    MyListAdapter adapter;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        //This gets the toolbar from the layout:
        Toolbar toolbar = findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(toolbar);

        progressbar = findViewById(R.id.progressbar);

        Intent i = getIntent();
        String latitude = i.getStringExtra("LATITUDE");
        String longitude = i.getStringExtra("LONGITUDE");

        new CitiesQuery().execute(
                "https://api.geodatasource.com/cities?key=OHY6WKLWXUKHOADTNUC0AFVCFCDX0GRN&lat="+latitude+"&lng="+longitude+"&format=JSON"
        );

        ListView list = findViewById(R.id.list);
        adapter = new MyListAdapter();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent citydetails = new Intent(CityFinderActivity.this, CityDetailsActivity.class);
                City city = citylist.get(position);
                citydetails.putExtra("CITY_NAME", city.getCity());
                citydetails.putExtra("COUNTRY_NAME", city.getCountry());
                citydetails.putExtra("REGION", city.getRegion());
                citydetails.putExtra("CURRENCY", city.getCurrency());
                citydetails.putExtra("LONGITUDE", city.getLongitude());
                citydetails.putExtra("LATITUDE", city.getLatitude());
                startActivity(citydetails);
            }
        });

    }

    /**
     * CitiesQuery Async class to get all the cities based on the passed longitude and latitude
     */
    class CitiesQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {

                URL url = new URL(strings[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONArray cityJsonArray = new JSONArray(result);

                for (int i = 0; i < cityJsonArray.length(); i++) {
                    JSONObject singleCityJsonObject = cityJsonArray.getJSONObject(i);
                    String city = singleCityJsonObject.getString("city");
                    String country = singleCityJsonObject.getString("country");
                    String region = singleCityJsonObject.getString("region");
                    String currency = singleCityJsonObject.getString("currency_name");
                    String longitude = singleCityJsonObject.getString("longitude");
                    String latitude = singleCityJsonObject.getString("latitude");
                    City c = new City(city, country, region, currency, longitude, latitude);
                    citylist.add(c);
                }

            } catch (Exception e)
            {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressbar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return citylist.size();
        }

        @Override
        public City getItem(int position) {
            return citylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            City city = getItem(position);
            View v = getLayoutInflater().inflate(R.layout.city_row, parent, false);

            TextView cityName = v.findViewById(R.id.cityName);
            cityName.setText(city.getCity());

            return v;
        }
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
