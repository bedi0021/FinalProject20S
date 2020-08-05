package com.example.finalproject20s;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "SavedMatches";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "FAVOURITE";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_EMBED = "EMBED";
    public final static String COL_SIDE1 = "SIDE1";
    public final static String COL_SIDE2 = "SIDE2";
    public final static String COL_THUMBNAIL = "THUBMNAIL";
    public final static String COL_DATE = "DATE";
    public final static String COL_ID = "_id";
    public final static String COL_URL = "URL";

    public DatabaseOpener(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT,"
                + COL_EMBED + " TEXT,"
                + COL_SIDE1 + " TEXT,"
                + COL_THUMBNAIL + " TEXT,"
                + COL_DATE + " TEXT,"
                + COL_SIDE2 + " TEXT,"
                + COL_URL  + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
