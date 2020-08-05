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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

/**
 * This application helps you to find lyrics for the popular songs around he world
 * Along with doing a google search for the desired song
 *
 * @author Harmanpreet Bedi
 * @version 1.0
 * @since 2020-05-08
 *
 */

public class FavouriteList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    SQLiteDatabase db;
    ArrayList<Song> songsList = new ArrayList<>();
    MyListAdapter myFavListAdapter;
    long newId;
    FrameLayout frame;
    DetailFragment frag = new DetailFragment();
    public static final String SHOW_SONG="Song: ",SHOW_ARTIST="Artist: ",SHOW_LYRICS="Lyrics: ",SHOW_ID="Id: ";

    ListView favList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        Bundle dataToPass = new Bundle();

        favList=(ListView)findViewById(R.id.favlist);
        frame=(FrameLayout)findViewById(R.id.FL);
        Toolbar tBar =(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(tBar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(FavouriteList.this);

        loadDataFromDatabase();
        myFavListAdapter=new MyListAdapter(FavouriteList.this,songsList);
        favList.setAdapter(myFavListAdapter);

        favList.setLongClickable(true);
        favList.setOnItemClickListener(((parent, view, position, id) -> {
            Song s = songsList.get(position);

            AlertDialog.Builder alt = new AlertDialog.Builder(FavouriteList.this);
            alt.setTitle(getResources().getString(R.string.delete));
            alt.setMessage(getResources().getString(R.string.deletethissong));
            alt.setNegativeButton(getResources().getString(R.string.no),(dialog, which) -> {
               dialog.dismiss();
            });
            alt.setPositiveButton(getResources().getString(R.string.yes),(dialog, which) -> {
                deleteSongFromList(position);
                deleteSong(s);
            });
            alt.setNeutralButton(getResources().getString(R.string.goToLyrics),(dialog, which) -> {
                dataToPass.putString(SHOW_SONG,songsList.get(position).getSongname());
                dataToPass.putString(SHOW_ARTIST,songsList.get(position).getArtist());
                dataToPass.putString(SHOW_LYRICS ,songsList.get(position).getLyrics());
                dataToPass.putLong(SHOW_ID,songsList.get(position).getId());

            DetailFragment dFrag = new DetailFragment();
            dFrag.setArguments(dataToPass);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.FL,dFrag)
                    .commit();
            frag=(DetailFragment) dFrag;

            Intent nextActivity = new Intent(FavouriteList.this, FragmentMade.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity);
            });
        alt.show();

        }));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sls_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                Intent profile = new Intent(FavouriteList.this, TopSearches.class);
                startActivity(profile);
                break;
            case R.id.helpmenuitem:
                AlertDialog.Builder alert = new AlertDialog.Builder(FavouriteList.this);
                alert.setTitle(getResources().getString(R.string.help));
                alert.setMessage(getResources().getString(R.string.helpinfo3));
                alert.setPositiveButton(getResources().getString(R.string.yes),(dialog, which)->{
                    Toast.makeText(FavouriteList.this,getResources().getString(R.string.glad),Toast.LENGTH_LONG).show();
                });
                alert.setNegativeButton(getResources().getString(R.string.no),(dialog, which)->{
                    Toast.makeText(FavouriteList.this,getResources().getString(R.string.feedback),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                });
                alert.show();
                break;
            case R.id.aboutProject:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(FavouriteList.this);
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
                AlertDialog.Builder alert2 = new AlertDialog.Builder(FavouriteList.this);
                alert2.setTitle(getResources().getString(R.string.appInstructions));
                alert2.setMessage(getResources().getString(R.string.appInstructions2));
                alert2.setNegativeButton(getResources().getString(R.string.close), (dialog, which) -> {dialog.dismiss();});
                alert2.show();
                break;
            case R.id.AboutApi:
                Intent profile = new Intent(FavouriteList.this, APIview.class);
                startActivity(profile);
                break;
            case R.id.donate:
                AlertDialog.Builder alert4 = new AlertDialog.Builder(FavouriteList.this);
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
                    Toast.makeText(FavouriteList.this, getResources().getString(R.string.thankDonate)+str+" "+getResources().getString(R.string.donation), Toast.LENGTH_LONG).show();
                });
                alert4.show();
                break;

        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
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

            songsList.add(new Song(id, artist, song, lyrics));
          }

    }

    /**
     * <p>This method deletes the item from arraylist, listview.</p>
     * @param position Clicking the item will get its position and this parameter can be used for that item selected
     *
     * @since 1.0
     */
    private void deleteSongFromList(int position){
        Song s = songsList.get(position);
        songsList.remove(position);
        myFavListAdapter.notifyDataSetChanged();
    }
    /**
     * <p>This method deletes the item from the database</p>
     * @param s Clicking the item will get its position and this parameter can be used to get the song on that position.
     *
     * @since 1.0
     */

    private void deleteSong(Song s){
        db.delete(DatabaseCreator.TABLE_NAME, DatabaseCreator.COL_ID + "= ?", new String[] {Long.toString(s.getId())});
    }
}