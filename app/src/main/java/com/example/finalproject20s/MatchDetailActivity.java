package com.example.finalproject20s;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject20s.MatchModel;
import com.example.finalproject20s.DatabaseOpener;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class MatchDetailActivity extends AppCompatActivity {

    MatchModel matchModel;
    TextView side1,side2,competition,date;
    ImageView image;
    Button liveScores,liveStream, favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);


        Bundle bundle = getIntent().getExtras();
        matchModel = (MatchModel) bundle.get("matchObj");
        competition= (TextView)findViewById(R.id.competition);
        liveScores=findViewById(R.id.liveScore);
        liveStream= findViewById(R.id.liveStream);
        favourites= findViewById(R.id.favourites);
        side1=(TextView) findViewById(R.id.side1);
        date= findViewById(R.id.date);
        side2=(TextView) findViewById(R.id.side2);
        image=(ImageView) findViewById(R.id.image2);
        competition.setText(matchModel.getCompetitionModel().getName());
        side1.setText(matchModel.getSide1().getName());
        side2.setText(matchModel.getSide2().getName());
        date.setText("ON: "+matchModel.getDate().substring(0,10));
        Picasso.with(getApplicationContext()).load(matchModel.getThumbnail()).fit().into(image);
         /**
          * this method takes to the page that shows the live Stream of  the  selected match  when Live Stream button from the layout page is clicked
          * @param v
          */
        liveStream.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(matchModel.getUrl()));
                startActivity(intent);
            }
        });
        /**
         * this method takes too the page that shows the score of the selected ,match when the Live Score button from the layout page is clicked
         */
        liveScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(matchModel.getCompetitionModel().getUrl()));
                startActivity(intent);
            }
        });
        /**
         * this method takes is used to add different matches to the list of favourites in a database ,which we can see in the favourites tab selected from the drawer.
         */
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOpener dbOpener = new DatabaseOpener (com.example.finalproject20s.MatchDetailActivity.this);
                SQLiteDatabase db = dbOpener.getWritableDatabase ();

               // Integer object = (Integer) item.getTag();
                favourites.setText(favourites.getText().equals("Favorite") ? "Saved" : "Favorite");

                ContentValues newRowValues = new ContentValues();

                newRowValues.put(DatabaseOpener.COL_TITLE, matchModel.getTitle());
                newRowValues.put(DatabaseOpener.COL_EMBED, matchModel.getEmbed());
                newRowValues.put(DatabaseOpener.COL_URL, matchModel.getUrl());
                newRowValues.put(DatabaseOpener.COL_THUMBNAIL, matchModel.getThumbnail());
                newRowValues.put(DatabaseOpener.COL_DATE, matchModel.getDate());
                newRowValues.put(DatabaseOpener.COL_SIDE1, matchModel.getSide1().getName());
                newRowValues.put(DatabaseOpener.COL_SIDE2, matchModel.getSide2().getName());
                db.insert(DatabaseOpener.TABLE_NAME,null,newRowValues);

                //Toast.makeText(MatchDetailActivity.this,"Added to Favourites",Toast.LENGTH_LONG).show ();
                String message = matchModel.getTitle()+" added to Favourites.";
                int duration = Snackbar.LENGTH_LONG;

                showSnackbar(v,message, duration);

            }


            /**
             * this method is to display Snackbar when the match is Added to favourites
             * @param view
             * @param message
             * @param duration
             */
            private void showSnackbar(View view,String message, int duration) {

                Snackbar.make(view,message, duration).show();
            }
        });

    }


}
