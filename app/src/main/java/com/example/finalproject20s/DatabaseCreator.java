package com.example.finalproject20s;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This application helps you to find lyrics for the popular songs around he world
 * Along with doing a google search for the desired song
 *
 * @author Harmanpreet Bedi
 * @version 1.0
 * @since 2020-05-08
 *
 */

/**
 * This class creates a database which will further use the favourite list to store the
 * favourite songs selected by the user
 *
 */
public class DatabaseCreator extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "SongsDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "Songs";
    public final static String COL_SONG = "SONG";
    public final static String COL_ARTIST = "ARTIST";
    public final static String COL_LYRICS = "LYRICS";
    public final static String COL_ID = "_id";

    public DatabaseCreator(Context ctx){super(ctx, DATABASE_NAME,null,VERSION_NUM);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COL_ARTIST+" text,"
                +COL_SONG+" text,"
                +COL_LYRICS+" text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
