package com.example.finalproject20s;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "CityDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "FAV_CITIES";
    public final static String COL_ID = "_id";
    public final static String COL_CITY_NAME = "COL_CITY_NAME";
    public final static String COL_COUNTRY = "COL_COUNTRY";
    public final static String COL_REGION = "COL_REGION";
    public final static String COL_CURRENCY = "COL_CURRENCY";
    public final static String COL_LONGITUDE = "COL_LONGITUDE";
    public final static String COL_LATITUDE = "COL_LATITUDE";

    public MyOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_CITY_NAME + " text,"
                + COL_COUNTRY + " text,"
                + COL_REGION + " text,"
                + COL_CURRENCY + " text,"
                + COL_LONGITUDE + " text,"
                + COL_LATITUDE + " text);");
    }


    //this function gets called if the database version on your device is lower than VERSION_NUM
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
