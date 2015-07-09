package com.example.spaceshipnavigator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ScoreHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    // static int id =

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String TABLE_NAME = ScoreDBContract.ScoreEntry.TABLE_NAME;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ScoreDBContract.ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreDBContract.ScoreEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ScoreDBContract.ScoreEntry.COLUMN_NAME_PERSON + TEXT_TYPE + COMMA_SEP +
                    ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE + INT_TYPE + " ) ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_CHECK_TABLE = "SELECT count(*) FROM sqlite_master WHERE type='table' AND name="+TABLE_NAME;

    public ScoreHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
    	dropTable(db);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void dropTable(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    static int id = 0;

    public void addEntry(SQLiteDatabase db, String person, String score) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ScoreDBContract.ScoreEntry.COLUMN_NAME_ENTRY_ID, id++);
        values.put(ScoreDBContract.ScoreEntry.COLUMN_NAME_PERSON, person);
        values.put(ScoreDBContract.ScoreEntry.COLUMN_NAME_SCORE, score);


// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                ScoreDBContract.ScoreEntry.TABLE_NAME,
                ScoreDBContract.ScoreEntry.COLUMN_NAME_NULLABLE,
                values);

    }


}