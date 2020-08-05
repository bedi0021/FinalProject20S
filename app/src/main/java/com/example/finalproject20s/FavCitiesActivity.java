package com.example.finalproject20s;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class FavCitiesActivity extends AppCompatActivity {

    /**
     * list to store cities
     */
    private ArrayList<City> citylist = new ArrayList<>();

    /**
     * declaration of MyListAdapter
     */
    private MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        //This gets the toolbar from the layout:
        Toolbar toolbar = findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(toolbar);

        findViewById(R.id.progressbar).setVisibility(View.GONE);


        ListView list = findViewById(R.id.list);
        adapter = new MyListAdapter();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent citydetails = new Intent(FavCitiesActivity.this, CityDetailsActivity.class);
                City city = citylist.get(position);
                citydetails.putExtra("ID", city.getId());
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

    @Override
    protected void onResume() {
        super.onResume();
        loadDataFromDatabase();
        adapter.notifyDataSetChanged();
    }

    /**
     * This method will retrieve favorite cities from the database
     */
    private void loadDataFromDatabase() {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {MyOpener.COL_ID, MyOpener.COL_CITY_NAME, MyOpener.COL_COUNTRY, MyOpener.COL_REGION, MyOpener.COL_CURRENCY, MyOpener.COL_LONGITUDE, MyOpener.COL_LATITUDE};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);
        int idColCityName = results.getColumnIndex(MyOpener.COL_CITY_NAME);
        int idColCountry = results.getColumnIndex(MyOpener.COL_COUNTRY);
        int idColRegion = results.getColumnIndex(MyOpener.COL_REGION);
        int idColCurrency = results.getColumnIndex(MyOpener.COL_CURRENCY);
        int idColLong = results.getColumnIndex(MyOpener.COL_LONGITUDE);
        int idColLat = results.getColumnIndex(MyOpener.COL_LATITUDE);

        citylist.clear();
        while (results.moveToNext()) {
            long id = results.getLong(idColIndex);

            City c = new City(
                    results.getString(idColCityName),
                    results.getString(idColCountry),
                    results.getString(idColRegion),
                    results.getString(idColCurrency),
                    results.getString(idColLong),
                    results.getString(idColLat)
            );

            c.setId(id);
            citylist.add(c);
        }

    }

    /**
     * MyListAdapter - the adapter class extends BaseAdapter to handle all the items of the list-view
     */
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
