package com.example.finalproject20s;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject20s.FavouriteAdapter;
import com.example.finalproject20s.MatchModel;
import com.example.finalproject20s.DatabaseOpener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FavouriteAdapter mAdapter;
    private RecyclerView rvMatches;
    private ArrayList<MatchModel> mArrMatches =  new ArrayList<>();
    private ProgressDialog mProgressDialog;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Toolbar tbar = (Toolbar) findViewById(R.id.fav_toolbar);
        setSupportActionBar(tbar);

        DrawerLayout drawer = findViewById(R.id.drawer_fav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,tbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_fav);
        navigationView.setNavigationItemSelectedListener(this);

        rvMatches = (RecyclerView) findViewById(R.id.rv_favourites);
        LinearLayoutManager llm = new LinearLayoutManager(com.example.finalproject20s.FavouritesActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMatches.setLayoutManager(llm);
        mProgressDialog= new ProgressDialog(com.example.finalproject20s.FavouritesActivity.this, R.style.App_DialogStyle);
        mProgressDialog.setMessage("Loading");
        loadMatchesFromDatabase();

        rvMatches.setAdapter(mAdapter);
    }

    /**
     * this method loads matches from the database known as favourites
     */
    private void loadMatchesFromDatabase() {
        DatabaseOpener dbOpener = new DatabaseOpener(this);
        database = dbOpener.getWritableDatabase();

        String[] columns = {DatabaseOpener.COL_ID, DatabaseOpener.COL_TITLE, DatabaseOpener.COL_SIDE1, DatabaseOpener.COL_URL, DatabaseOpener.COL_SIDE2, DatabaseOpener.COL_THUMBNAIL,DatabaseOpener.COL_DATE};

        Cursor results = database.query(false, DatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColIndex = results.getColumnIndex(DatabaseOpener.COL_ID);
        int titleColumnIndex = results.getColumnIndex(DatabaseOpener.COL_TITLE);
        int side1ColumnIndex = results.getColumnIndex(DatabaseOpener.COL_SIDE1);
        int urlColumnIndex = results.getColumnIndex(DatabaseOpener.COL_URL);
        int imageColumnIndex = results.getColumnIndex(DatabaseOpener.COL_THUMBNAIL);
        int dateColumnIndex = results.getColumnIndex(DatabaseOpener.COL_DATE);

        while (results.moveToNext())
        {
            long id = results.getLong(idColIndex);
            String title = results.getString(titleColumnIndex);
            String side1 = results.getString(side1ColumnIndex);
            String url = results.getString(urlColumnIndex);
            String image = results.getString(imageColumnIndex);
            String date = results.getString(dateColumnIndex);

            mArrMatches.add(new MatchModel(id, title, side1, url,image,date));

        }
        rvMatches.setAdapter(mAdapter);
        mAdapter=new FavouriteAdapter(mArrMatches, com.example.finalproject20s.FavouritesActivity.this);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * navigates the user to nextActivity
     * @param item
     * @return
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemnav3:
                startActivity(new Intent(com.example.finalproject20s.FavouritesActivity.this,Main.class));
                break;
        }
        return true;
    }
}
